package com.example.Hackathon.service;

import com.example.Hackathon.dto.PostDtoAll;
import com.example.Hackathon.dto.VacancyAllDto;
import com.example.Hackathon.dto.VacancyDto;
import com.example.Hackathon.entity.*;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.UserRepository;
import com.example.Hackathon.repository.VacancyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class VacancyService {

    @Autowired
    VacancyRepo vacancyRepo;

    @Autowired
    UserService userService;

    public List<VacancyAllDto> getAllVacancies()
    {
        List<Vacancy> list = vacancyRepo.findByStatus(Status.ACTIVATE);
        List<VacancyAllDto> result = new ArrayList<>();
        for (Vacancy vacancy : list) {
            VacancyAllDto model = new VacancyAllDto();
            model.setDescription(vacancy.getDescription());
            model.setVacancy_topic(vacancy.getVacancy_topic());
            model.setCompanyName(vacancy.getCompanyName());
            model.setDate(vacancy.getDate());
            model.setNickname(vacancy.getUser().getNickname());
            model.setCountryCode(vacancy.getUser().getCountryCode());
            result.add(model);
        }
        return result;
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
