package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Comments;
import com.example.Hackathon.entity.Message;
import com.example.Hackathon.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
