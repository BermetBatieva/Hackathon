package com.example.Hackathon.dto;

import com.example.Hackathon.entity.Category;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {

    private String topic; // тема

    private String description; // описание

    private Long categoryId;

}
