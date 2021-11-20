package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Image;
import com.example.Hackathon.entity.News;
import com.example.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {

    Image findByUser_Id(Long id);

    List<Image> findByPosts_Id(Long id);

    Image findByNews_Id(Long id);
}
