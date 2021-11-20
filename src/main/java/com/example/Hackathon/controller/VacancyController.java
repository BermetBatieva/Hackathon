package com.example.Hackathon.controller;

import com.example.Hackathon.dto.VacancyDto;
import com.example.Hackathon.entity.Vacancy;
import com.example.Hackathon.service.VacancyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("vacancy")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @ApiOperation(value = "Создать вакансию")
    @PostMapping("/create")
    public ResponseEntity<Vacancy> createVacancy(@RequestBody VacancyDto vacancyDto)
    {
        return new ResponseEntity<>(vacancyService.createVacancy(vacancyDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Удалить вакансию")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Vacancy> deleteVacancy(@PathVariable Long id)
    {
        return new ResponseEntity<>(vacancyService.deleteVacancy(id), HttpStatus.OK);
    }
}
