package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findByStatus(Status status);

    Vacancy findByUser(User user);
}
