package com.example.Hackathon.service;

import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.dto.PostDtoByCategory;
import com.example.Hackathon.entity.*;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.CategoryRepo;
import com.example.Hackathon.repository.CommentsRepo;
import com.example.Hackathon.repository.ImageRepo;
import com.example.Hackathon.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            model.setNickname(posts.getUser().getNickname());
            model.setUserId(posts.getUser().getId());
            List<Comments> commentsList = commentsRepo.findByPosts_IdAndStatus(posts.getId(),Status.ACTIVATE);

            model.setCommentsList(commentsList);

            System.out.println(commentsList.get(0));
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






}
