package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Posts,Long> {

    Posts findByUser_Id(Long id);
}
