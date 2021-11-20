package com.example.Hackathon.controller;


import com.example.Hackathon.dto.MessageDto;
import com.example.Hackathon.dto.PostDtoAll;
import com.example.Hackathon.entity.Message;
import com.example.Hackathon.service.MessageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @ApiOperation(value = "Отправить сообщение")
    @PostMapping("/send-message")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto)
    {
        return new ResponseEntity<>(messageService.sendMessage(messageDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить сообщение")
    @DeleteMapping("/delete-message/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable Long id)
    {
        return new ResponseEntity<>(messageService.deleteMessage(id), HttpStatus.OK);
    }

    @GetMapping("all-chats")
    public List<Message> getAllPosts(){
        return messageService.getAllChats();
    }
}
