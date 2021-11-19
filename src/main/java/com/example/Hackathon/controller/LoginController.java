package com.example.Hackathon.controller;

import com.example.Hackathon.dto.*;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.Image;
import com.example.Hackathon.jwt.JwtUtils;
import com.example.Hackathon.service.GroupService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

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


    @Autowired
    private GroupService groupService;


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

    @PostMapping("/register-new-user")
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
    @GetMapping("/get-current-user")
    public UserDto getCurrentUser(Principal principal){
        return userService.retrieveCurrentUser(principal);
    }


    @PutMapping("/image")
    public ResponseEntity<Image> setImage(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException, IOException {
        return userService.setImage(multipartFile);
    }

    @ApiOperation(value = "Вход в группу нужно только code")
    @PostMapping("/join-to-group")
    public ResponseEntity<Group> joinToGroup(@RequestBody GroupDto groupDto)
    {
        return new ResponseEntity<>(groupService.joinGroup(groupDto.getCode()),
                HttpStatus.OK);
    }

    @GetMapping("get-my-group")
    public ResponseEntity<GroupDto> getGroupByCurrentUser() {
        return new ResponseEntity<>(groupService.getMyGroup(),
                HttpStatus.OK);
    }

//    @GetMapping("get-by-group-id/{id}")
//    public ResponseEntity<GroupDto> getById(@PathVariable Long id){
//        return new ResponseEntity<>(groupService.getGroupById(id),
//                HttpStatus.OK);
//    }

}
