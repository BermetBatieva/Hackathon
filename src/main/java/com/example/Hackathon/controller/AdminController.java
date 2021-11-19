package com.example.Hackathon.controller;

import com.example.Hackathon.dto.GroupDto;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.service.GroupService;
import com.example.Hackathon.service.PostsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private PostsService postsService;

    @DeleteMapping("post/{id}")
    public ResponseEntity<Posts> deletePost(@PathVariable Long id){
        return new ResponseEntity<>(postsService.deletePost(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Создание группы")
    @PostMapping("/create-new-group")
    public ResponseEntity<Group> createNewGroup(@RequestBody GroupDto groupDto)
    {
        return new ResponseEntity<>(groupService.createGroup(groupDto), HttpStatus.OK);
    }


}
