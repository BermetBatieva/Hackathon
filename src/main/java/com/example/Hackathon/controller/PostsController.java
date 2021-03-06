package com.example.Hackathon.controller;

import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.dto.PostDtoAll;
import com.example.Hackathon.dto.PostDtoByCategory;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.service.PostsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @ApiOperation(value = "стянуть с commets")
    @GetMapping("all/{categoryId}")
    public List<PostDtoByCategory> getAllPostInMainByCategory(@PathVariable Long categoryId){
        return postsService.allPostByCategory(categoryId);
    }


    @ApiOperation(value = "много фоток на посты")
    @PutMapping("/image/{id}")
    public ResponseEntity<Posts> setImage(@RequestParam(name = "file") MultipartFile[] multipartFile,
                                          @PathVariable Long id) throws IOException, IOException {
        return postsService.setImage(multipartFile,id);
    }

    @GetMapping("all")
    public List<PostDtoAll> getAllPosts(){
        return postsService.getAll();
    }
}
