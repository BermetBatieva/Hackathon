package com.example.Hackathon.service;

import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.entity.Category;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.CategoryRepo;
import com.example.Hackathon.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class PostsService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepo categoryRepo;


    public PostDto addNewPost(PostDto postDto,Principal principal){
        Posts posts  = new Posts();
        posts.setUser(userService.getCurrentUser());
        posts.setDescription(postDto.getDescription());
        posts.setTopic(postDto.getTopic());
        posts.setCategory(categoryRepo.findById(postDto.getCategoryId()).
                orElseThrow( () -> new ResourceNotFoundException("нет категории с такой id")));
        posts.setDate(postDto.getDate());
        postRepo.save(posts);

        return postDto;

    }
}
