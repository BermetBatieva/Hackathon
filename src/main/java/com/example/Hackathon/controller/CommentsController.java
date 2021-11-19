package com.example.Hackathon.controller;


import com.example.Hackathon.dto.CommentDto;
import com.example.Hackathon.service.CommentsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;


    @ApiOperation(value = "все коменты по посту")
    @GetMapping("get-all-by-post/{postId}")
    public List<CommentDto> getAllByPost(@PathVariable Long postId){
        return commentsService.getAllByPost(postId);
    }

}
