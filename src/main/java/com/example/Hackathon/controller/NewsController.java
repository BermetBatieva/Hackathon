package com.example.Hackathon.controller;

import com.example.Hackathon.dto.PostDtoByCategory;
import com.example.Hackathon.entity.News;
import com.example.Hackathon.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("news")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @GetMapping("all")
    public List<News> getAllPostInMainByCategory(){
        return newsService.getAll();
    }

}
