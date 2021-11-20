package com.example.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDto {

    private Long id;

    private  String vacancy_topic;

    private String description;

    private Date date;

    private String companyName;

}