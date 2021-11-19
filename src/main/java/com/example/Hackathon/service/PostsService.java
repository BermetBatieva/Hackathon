package com.example.Hackathon.service;

import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {

    @Autowired
    private PostRepo postRepo;


//    public PostDto addNewPost(PostDto postDto){
//        Posts posts  = new Posts();
//        posts.setDescription(postDto.getDescription());
//
//    }
}
