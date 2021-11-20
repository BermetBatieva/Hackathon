package com.example.Hackathon.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Hackathon.dto.NewsDto;
import com.example.Hackathon.entity.Image;
import com.example.Hackathon.entity.News;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.ImageRepo;
import com.example.Hackathon.repository.NewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private UserService userService;

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


    public ResponseEntity<Image> setImage(MultipartFile multipartFile,Long newsId) throws IOException {

        final String urlKey = "cloudinary://513184318945249:-PXAzPrMMtx1J7NCL1afdr59new@neobis/"; //в конце добавляем '/'
        Image image = new Image();
        News news = newsRepo.findById(newsId).orElseThrow(
                () -> new ResourceNotFoundException("нет такой id",newsId)
        );
        File file;
        try{
            file = Files.createTempFile(System.currentTimeMillis() + "",
                            multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length()-4)) // .jpg
                    .toFile();
            multipartFile.transferTo(file);

            Cloudinary cloudinary = new Cloudinary(urlKey);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            image.setNews(news);
            image.setName((String) uploadResult.get("public_id"));
            image.setUrl((String) uploadResult.get("url"));
            image.setFormat((String) uploadResult.get("format"));
            imageRepo.save(image);


            return ResponseEntity.ok().body(image);
        }catch (IOException e){
            throw new IOException("User was unable to set a image");
        }
    }

    public List<News> getAll(){
        List<News> news = newsRepo.findByStatus(Status.ACTIVATE);
        return  news;
    }

}
