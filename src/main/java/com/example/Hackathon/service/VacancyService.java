package com.example.Hackathon.service;

import com.example.Hackathon.dto.VacancyDto;
import com.example.Hackathon.entity.Comments;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.Vacancy;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.UserRepository;
import com.example.Hackathon.repository.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class VacancyService {

    @Autowired
    VacancyRepo vacancyRepo;

    @Autowired
    UserService userService;

    public List<Vacancy> getAllVacancies()
    {
        return vacancyRepo.findAll();
    }

    public Vacancy createVacancy(VacancyDto vacancyDto)
    {
        Vacancy vacancy = new Vacancy();
        vacancy.setStatus(Status.ACTIVATE);
        vacancy.setCompanyName(vacancyDto.getCompanyName());
        vacancy.setVacancy_topic(vacancyDto.getVacancy_topic());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setUser(userService.getCurrentUser());
        LocalDateTime localDateTime = LocalDateTime.now();
        vacancy.setDate(convertLocalDateTimeToDateUsingInstant(localDateTime));
        vacancyRepo.save(vacancy);

        return vacancy;
    }

    public Vacancy deleteVacancy(Long id)
    {
        Vacancy vacancy = vacancyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Нет вакансии с таким ID ",id));
        if (Objects.equals(userService.getCurrentUser().getId(), vacancy.getUser().getId()))
        {
            vacancy.setStatus(Status.DELETED);
            vacancyRepo.save(vacancy);
            return vacancy;
        }
        else{
            throw new ResourceNotFoundException("Невозможно удалить вакансию");
        }
    }

    Date convertLocalDateTimeToDateUsingInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }




}
