package com.example.Hackathon.controller;

import com.example.Hackathon.dto.AuthenticationResponse;
import com.example.Hackathon.dto.UserDto;
import com.example.Hackathon.dto.UserLogin;
import com.example.Hackathon.dto.UserRegister;
import com.example.Hackathon.entity.Image;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.AlreadyExistException;
import com.example.Hackathon.jwt.JwtUtils;
import com.example.Hackathon.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtil;


    @Autowired
    private UserService userService;


    @ApiOperation(value = "Авторизация пользователей. (Получение токена)")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserLogin userDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password", e);
        }

        final UserDetails userDetails = userService
                .loadUserByUsername(userDto.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("register-new-user")
    public ResponseEntity<AuthenticationResponse> addNewUser(@RequestBody UserRegister userDto) throws Exception {
        userService.create(userDto);
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password", e);
        }
        final UserDetails userDetails = userService
                .loadUserByUsername(userDto.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    @ApiOperation(value = "Данные пользователя")
    @GetMapping("get-current-user")
    public ResponseEntity<UserDto> getCurrentUser(){
        UserDto model = userService.retrieveCurrentUser();
        return ResponseEntity.ok(model);
    }


    @PutMapping("/image")
    public ResponseEntity<Image> setImage(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException, IOException {
        return userService.setImage(multipartFile);
    }


}
