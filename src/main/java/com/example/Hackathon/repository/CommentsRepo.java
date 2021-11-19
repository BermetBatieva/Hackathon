package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Comments;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepo extends JpaRepository<Comments,Long> {

    List<Comments> findByPostsAndStatus(Posts posts,Status status);

    Comments findByUser(User user);


}