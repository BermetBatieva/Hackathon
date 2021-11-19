package com.example.Hackathon.dto;

import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {

    private Long id;

    private Long userId;

    private String comments;



}