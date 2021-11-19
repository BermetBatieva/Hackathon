package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Comments;
import com.example.Hackathon.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepo extends JpaRepository<Comments,Long> {
}
