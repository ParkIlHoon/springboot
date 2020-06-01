package com.ilhoon.demo.service;

import com.ilhoon.demo.repository.UserRepository;
import com.ilhoon.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{

    @Autowired
    private UserRepository userRepository;

    public List<User> findByUserId (String userId)
    {
        List<User> userList = new ArrayList<>();
        userList = userRepository.findByUserId(userId);

        return userList;
    }
}
