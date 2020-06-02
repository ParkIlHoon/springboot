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

    public List<User> findAll ()
    {
        return userRepository.findAll();
    }

    public User findByUserId (String userId)
    {
        return userRepository.findByUserId(userId);
    }

    public List<User> findByUserNm (String userNm)
    {
        return userRepository.findByUserNm(userNm);
    }

    public List<User> findByUserNmLike (String userNm)
    {
        return userRepository.findByUserNmLike(userNm);
    }

    public User save (User userVO)
    {
        userRepository.save(userVO);
        return userVO;
    }

    public void deleteByUserId (User userVO)
    {
        userRepository.delete(userVO);
    }
}
