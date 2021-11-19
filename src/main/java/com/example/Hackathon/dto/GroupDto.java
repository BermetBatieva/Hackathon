package com.example.Hackathon.dto;


import com.example.Hackathon.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupDto {
    private Long id;

    private String name;

    private Long code;

    private List<Posts> posts;


}