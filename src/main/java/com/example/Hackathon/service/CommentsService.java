package com.example.Hackathon.service;

import com.example.Hackathon.dto.CommentDto;
import com.example.Hackathon.entity.Comments;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.CommentsRepo;
import com.example.Hackathon.repository.PostRepo;
import com.example.Hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Service
public class CommentsService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PostsService postsService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    CommentsRepo commentsRepo;

    public Comments deleteComment(Long id){
        Comments comments = commentsRepo.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Нет комментария с таким ID ",id));
        if (Objects.equals(userService.getCurrentUser().getId(), comments.getUser().getId()))
        {
            comments.setStatus(Status.DELETED);
            commentsRepo.save(comments);
            return comments;
        }
        else{
            throw new ResourceNotFoundException("Невозможно удалить комментарий");
        }
    }

    public Comments addComment(CommentDto commentDto)
    {
        Comments comments = new Comments();
        LocalDateTime localDateTime = LocalDateTime.now();
        comments.setStatus(Status.ACTIVATE);
        comments.setUser(userService.getCurrentUser());
        comments.setComments(commentDto.getComments());
        comments.setDate(convertLocalDateTimeToDateUsingInstant(localDateTime));
        Posts posts = postRepo.findByUser_Id(commentDto.getUserId());
        comments.setPosts(posts);
        commentsRepo.save(comments);
        return comments;
    }

    private Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

}