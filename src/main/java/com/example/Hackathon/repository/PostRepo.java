package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Stack;

@Repository
public interface PostRepo extends JpaRepository<Posts,Long> {

    Posts findByUser_Id(Long id);

    List<Posts> findByGroup_IdAndStatus(Long id, Status status);

    List<Posts> findByStatus(Status status);

    List<Posts> findByCategory_IdAndStatusAndGroup_Id(Long id,Status status,Long idGroup);
}
