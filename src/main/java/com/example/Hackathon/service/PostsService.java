package com.example.Hackathon.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.dto.PostDtoAll;
import com.example.Hackathon.dto.PostDtoByCategory;
import com.example.Hackathon.entity.*;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.CategoryRepo;
import com.example.Hackathon.repository.CommentsRepo;
import com.example.Hackathon.repository.ImageRepo;
import com.example.Hackathon.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PostsService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private CommentsRepo commentsRepo;


    public Posts addNewPost(PostDto postDto){
        Posts posts  = new Posts();
        posts.setStatus(Status.ACTIVATE);
        posts.setUser(userService.getCurrentUser());
        posts.setDescription(postDto.getDescription());
        posts.setTopic(postDto.getTopic());
        posts.setCategory(categoryRepo.findById(postDto.getCategoryId()).
                orElseThrow( () -> new ResourceNotFoundException("нет категории с такой id")));
        LocalDateTime localDateTime = LocalDateTime.now();
        posts.setDate(convertLocalDateTimeToDateUsingInstant(localDateTime));
        postRepo.save(posts);

        return posts;
    }

    Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public Posts deletePost(Long id){
        Posts posts = postRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("нет поста с таким id ",id));
        posts.setStatus(Status.DELETED);
        postRepo.save(posts);

        return posts;
    }


    public List<PostDtoByCategory> allPostByCategory(long categoryId) {
        List<Posts> list = postRepo.findByCategory_IdAndStatusAndGroup_Id(categoryId,
                Status.ACTIVATE,null);
        List<PostDtoByCategory> result = new ArrayList<>();
        for (Posts posts : list) {
            PostDtoByCategory model = new PostDtoByCategory();
            model.setNickname(posts.getUser().getEmail());
            model.setUserId(posts.getUser().getId());
            List<Image> image = imageRepo.findByPosts_Id(posts.getId());

            List<String> url = new ArrayList<>();
            for(Image i : image ){
                url.add(i.getUrl());
            }
            model.setUrlImage(url);
            model.setCategoryId(posts.getCategory().getId());
            model.setTopic(posts.getTopic());
            model.setDescription(posts.getDescription());
            result.add(model);
        }
        return result;
    }


    public ResponseEntity<Posts> setImage(MultipartFile[] files, Long postId) throws IOException {
        final String urlKey = "cloudinary://513184318945249:-PXAzPrMMtx1J7NCL1afdr59new@neobis/";
        List<Image> images = new ArrayList<>();
        Posts posts = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("нет здания с таким id = ", postId)
        );

        Arrays.asList(files).forEach(file -> {

            File file1;
            try {
                file1 = Files.createTempFile(System.currentTimeMillis() + "",
                                Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().length() - 4))
                        .toFile();
                file.transferTo(file1);

                Cloudinary cloudinary = new Cloudinary(urlKey);
                Map uploadResult = cloudinary.uploader().upload(file1, ObjectUtils.emptyMap());

                Image image = new Image();
                image.setPosts(posts);
                image.setName((String) uploadResult.get("public_id"));
                image.setUrl((String) uploadResult.get("url"));
                image.setFormat((String) uploadResult.get("format"));

                images.add(imageRepo.save(image));



            } catch (IOException e) {
                try {
                    throw new IOException("failed to install image");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return ResponseEntity.ok().body(posts);
    }

    public List<PostDtoAll> getAll() {
        List<Posts> list = postRepo.findByStatus(Status.ACTIVATE);
        List<PostDtoAll> result = new ArrayList<>();
        for (Posts posts : list) {
            PostDtoAll model = new PostDtoAll();
            model.setDescription(posts.getDescription());
            model.setTopic(posts.getTopic());
            List<String>  url = new ArrayList<>();
            List<Image> imageList = imageRepo.findByPosts_Id(posts.getId());
            for(Image i : imageList ){
                url.add(i.getUrl());
            }
            model.setUrlImage(url);
            model.setNickname(posts.getUser().getNickname());
            model.setId(posts.getId());

            result.add(model);
        }
        return result;
    }






}
