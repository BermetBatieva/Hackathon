package com.example.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDtoAll {

    private String topic; // тема

    private String description; // описание

    private String nickname;

    private Long userId;

    private List<String> urlImage;

    private List<CommentDto> commentDtos;
}
