package com.example.Hackathon.repository;

import com.example.Hackathon.entity.News;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News,Long> {

    List<News> findByStatus(Status status);
}
