package com.example.Hackathon.service;

import com.example.Hackathon.dto.MessageDto;
import com.example.Hackathon.entity.Message;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.MessageRepo;
import com.example.Hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private UserService userService;

    @Autowired
    MessageRepo messageRepo;

    public Message sendMessage(MessageDto messageDto)
    {
        Message message = new Message();
        LocalDateTime localDateTime = LocalDateTime.now();
        message.setStatus(Status.ACTIVATE);
        message.setUser(userService.getCurrentUser());
        message.setMessage(messageDto.getMessage());
        message.setDate(convertLocalDateTimeToDateUsingInstant(localDateTime));
        messageRepo.save(message);
        return message;

    }

    public Message deleteMessage(Long id)
    {
        Message message = messageRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("нет сообщения с таким id ",id));
        if (userService.getCurrentUser().getId() == message.getUser().getId())
        {
            message.setStatus(Status.DELETED);
            messageRepo.save(message);
            return message;
        }
        else{
            throw new ResourceNotFoundException("Невозможно удалить сообщение");
        }


    }

    private Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }


   public List<Message>  getAllChats(){
      List<Message> messageList =  messageRepo.findByStatus(Status.ACTIVATE);

      return messageList;
   }


}