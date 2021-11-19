package com.example.Hackathon.controller;

import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("posts")
public class PostsController {

    @Autowired
    private PostsService postsService;


    @PostMapping("add-new-post")
    public ResponseEntity<Posts> addPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postsService.addNewPost(postDto), HttpStatus.OK);
    }
}
