package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Posts,Long> {
}
