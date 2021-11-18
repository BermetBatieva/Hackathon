package com.example.Hackathon.controller;

import com.example.Hackathon.dto.AuthenticationResponse;
import com.example.Hackathon.dto.UserDto;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.AlreadyExistException;
import com.example.Hackathon.jwt.JwtUtils;
import com.example.Hackathon.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserDto userDto) throws Exception {
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
    public ResponseEntity<User> addNewUser(@RequestBody UserDto model) throws AlreadyExistException {
        return new ResponseEntity<>( userService.create(model), HttpStatus.OK);
    }
}
