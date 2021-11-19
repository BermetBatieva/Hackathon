package com.example.Hackathon.service;

import com.example.Hackathon.dto.NewsDto;
import com.example.Hackathon.entity.News;
import com.example.Hackathon.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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

}
