package com.example.Hackathon.repository;

import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepo extends JpaRepository<Group,Long> {

    Group findByCode(Long code);

}
