package com.example.Hackathon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vacancies")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String vacancy_topic;

    @ManyToOne
    private User user;

    private String description;

    private Date date;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private Status status;

}
