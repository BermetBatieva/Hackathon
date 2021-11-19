package com.example.Hackathon.service;

import com.example.Hackathon.dto.NewsDto;
import com.example.Hackathon.entity.News;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepo newsRepo;

    public News addNews(NewsDto newsDto){
        News news = new News();
        LocalDateTime localDateTime = LocalDateTime.now();
        news.setDate(convertLocalDateTimeToDateUsingInstant(localDateTime));
        news.setDescription(newsDto.getDescription());
        news.setTopic(newsDto.getTopic());
        news.setUrlWebSite(newsDto.getUrl());
        newsRepo.save(news);
        return news;
    }

    private Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

//    public List<News> getAllNews(){
//        List<News> newsList = newsRepo.findByStatus(Status.ACTIVATE);
//        List<MenuForBarista> result = new ArrayList<>();
//    }

}
