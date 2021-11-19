package com.example.Hackathon.service;

import com.example.Hackathon.dto.UserDto;
import com.example.Hackathon.entity.ERole;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.AlreadyExistException;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.GroupRepo;
import com.example.Hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    private GroupRepo groupRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("пользователь с таким email не существует!"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }


    public User getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("пользователь с таким email не существует!"));
        return user;
    }


    public  User create(UserDto userDto) throws AlreadyExistException {
        User user = new User();
        user.setUserPassword(encoder.encode(userDto.getPassword()));
        user.setRole(ERole.ROLE_USER);
        if(!userRepository.existsByEmail(userDto.getEmail()))
            user.setEmail(userDto.getEmail());
        else
            throw  new AlreadyExistException("User email" + userDto.getEmail() + "существует");
        user.setNickname(userDto.getNickname());
        user.setCodeForGroup(userDto.getCodeForGroup());
       if (groupRepo.findByCode(userDto.getCodeForGroup()) == null)
       {
           throw new ResourceNotFoundException("Group not found");
       }
       else{
           user.setGroup(groupRepo.findByCode(userDto.getCodeForGroup()));
       }

        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setDateOfBirth(userDto.getDateOfBirth());
        userRepository.save(user);
        return user;
    }
}