package com.example.Hackathon.service;

import com.example.Hackathon.dto.GroupDto;
import com.example.Hackathon.dto.PostDto;
import com.example.Hackathon.entity.Group;
import com.example.Hackathon.entity.Posts;
import com.example.Hackathon.entity.Status;
import com.example.Hackathon.entity.User;
import com.example.Hackathon.exception.ResourceNotFoundException;
import com.example.Hackathon.repository.GroupRepo;
import com.example.Hackathon.repository.PostRepo;
import com.example.Hackathon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService{

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepo postRepo;

    public Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setCode(groupDto.getCode());
        groupRepo.save(group);
        return group;
    }

    public Group joinGroup(Long code)
    {
        User curUser = userService.getCurrentUser();
        if (groupRepo.findByCode(code) == null)
        {
            throw new ResourceNotFoundException("Group not found");
        }
        else{
            curUser.setGroup(groupRepo.findByCode(code));
            userRepository.save(curUser);

            return groupRepo.findByCode(code);
        }
    }

    public GroupDto getMyGroup(){
        User curUser = userService.getCurrentUser();
        Group group = curUser.getGroup();
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setCode(group.getCode());
        groupDto.setName(group.getName());
        return groupDto;
    }

    public GroupDto getGroupById(Long id){
        Group group = groupRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("нет группы с такой id ", id));
        GroupDto groupDto = new GroupDto();

        groupDto.setName(group.getName());
        groupDto.setId(group.getId());

        List<Posts> posts = postRepo.findByGroup_IdAndStatus(id, Status.ACTIVATE);
        groupDto.setPosts(posts);

        return groupDto;


    }

    private PostDto convertator(Posts posts){
        PostDto postDto = new PostDto();

        postDto.setDescription(posts.getDescription());
        postDto.setTopic(posts.getTopic());
        postDto.setGroupName(posts.getGroup().getName());

        return postDto;

    }

//    public
}
