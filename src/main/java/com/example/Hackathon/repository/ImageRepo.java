package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Image;
import com.example.Hackathon.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {
}
