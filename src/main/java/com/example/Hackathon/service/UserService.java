package com.example.Hackathon.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Hackathon.dto.UserDto;
import com.example.Hackathon.dto.UserRegister;
import com.example.Hackathon.entity.ERole;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.Image;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.AlreadyExistException;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.GroupRepo;
import com.example.Hackathon.repository.ImageRepo;
import com.example.Hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ImageRepo imageRepo;


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


    public  User create(UserRegister userDto) throws AlreadyExistException {
        User user = new User();
        user.setUserPassword(encoder.encode(userDto.getPassword()));
        user.setRole(ERole.ROLE_USER);
        user.setCountryCode(userDto.getCountryCode());
        if(!userRepository.existsByEmail(userDto.getEmail()))
            user.setEmail(userDto.getEmail());
        else
            throw  new AlreadyExistException("User email" + userDto.getEmail() + "существует");
        user.setNickname(userDto.getNickname());
        user.setFirstName(userDto.getFirstname());
        user.setLastName(userDto.getLastname());
        user.setDateOfBirth(userDto.getDateOfBirth());
        userRepository.save(user);
        return user;
    }

    public User getCurrentUser(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userEmail= authentication.getName();
        return getByEmail(userEmail);
    }

    public UserDto retrieveCurrentUser(Principal principal) {
        UserDto model = new UserDto();
        User user = getCurrentUser();
        model.setEmail(user.getEmail());

        Image image = imageRepo.findByUser_Id(user.getId());
        if(image == null)
            model.setUrlImage(null);
        else
            model.setUrlImage(image.getUrl());
        model.setDateOfBirth(user.getDateOfBirth());
        model.setFirstname(user.getFirstName());
        model.setNickname(user.getNickname());
        model.setLastname(user.getLastName());
        model.setCountryCode(user.getCountryCode());
        return model;
    }


    public ResponseEntity<Image> setImage(MultipartFile multipartFile) throws IOException {

        final String urlKey = "cloudinary://513184318945249:-PXAzPrMMtx1J7NCL1afdr59new@neobis/"; //в конце добавляем '/'
        Image image = new Image();
        User user = getCurrentUser();
        File file;
        try{
            file = Files.createTempFile(System.currentTimeMillis() + "",
                            multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length()-4)) // .jpg
                    .toFile();
            multipartFile.transferTo(file);

            Cloudinary cloudinary = new Cloudinary(urlKey);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            image.setUser(user);
            image.setName((String) uploadResult.get("public_id"));
            image.setUrl((String) uploadResult.get("url"));
            image.setFormat((String) uploadResult.get("format"));
            imageRepo.save(image);


            return ResponseEntity.ok().body(image);
        }catch (IOException e){
            throw new IOException("User was unable to set a image");
        }
    }
}