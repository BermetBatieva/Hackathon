package com.example.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String nickname;

    private Date dateOfBirth;

    private String countryCode;

    private String group;

    private String urlImage;
}
