package com.example.Hackathon.service;

import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.entity.Category;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.CategoryRepo;
import com.example.Hackathon.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PostsService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepo categoryRepo;


    public Posts addNewPost(PostDto postDto){
        Posts posts  = new Posts();
        posts.setStatus(Status.ACTIVATE);
        posts.setUser(userService.getCurrentUser());
        posts.setDescription(postDto.getDescription());
        posts.setTopic(postDto.getTopic());
        posts.setCategory(categoryRepo.findById(postDto.getCategoryId()).
                orElseThrow( () -> new ResourceNotFoundException("нет категории с такой id")));
        LocalDateTime localDateTime = LocalDateTime.now();
        posts.setDate(convertLocalDateTimeToDateUsingInstant(localDateTime));
        postRepo.save(posts);

        return posts;
    }

    Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
