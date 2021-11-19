package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Image;
import com.example.Hackathon.entity.News;
import com.example.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {

    Image findByUser_Id(Long id);
}
