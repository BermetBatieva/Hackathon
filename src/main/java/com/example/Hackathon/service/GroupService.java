package com.example.Hackathon.service;

import com.example.Hackathon.dto.GroupDto;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.GroupRepo;
import com.example.Hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService{

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setCode(groupDto.getCode());
        groupRepo.save(group);
        return group;
    }

    public Boolean joinGroup(Long code)
    {
        User curUser = userService.getCurrentUser();
        if (groupRepo.findByCode(code) == null)
        {
            throw new ResourceNotFoundException("Group not found");
        }
        else{
            curUser.setGroup(groupRepo.findByCode(code));
            userRepository.save(curUser);

            return true;
        }
    }

    public Group getMyGroup()
    {
        User curUser = userService.getCurrentUser();

        return curUser.getGroup();
    }
}
