package com.example.Hackathon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private String nickname;

    private Long codeForGroup;

    private Date dateOfBirth;

}
