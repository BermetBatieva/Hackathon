package com.example.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VacancyAllDto {

    private Long id;

    private  String vacancy_topic;

    private String description;

    private Date date;

    private String companyName;

    private String countryCode;

    private String nickname;
}
