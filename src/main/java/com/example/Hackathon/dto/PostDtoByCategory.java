package com.example.Hackathon.dto;

import com.example.Hackathon.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDtoByCategory {

    private String topic; // тема

    private String description; // описание

    private Long categoryId;

    private String nickname;

    private List<String> urlImage;

    private Long userId;

    private List<Comments> commentsList;

}
