package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepo extends JpaRepository<Vacancy, Long> {
}
