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

    /**
     * 전체 사용자 목록을 조회한다.
     * @return 전체 사용자 목록
     */
    public List<User> findAll ()
    {
        return userRepository.findAll();
    }

    /**
     * 사용자 아이디에 해당하는 사용자를 조회한다.
     * @param userId 조회할 사용자의 아이디
     * @return 사용자 아이디에 해당하는 사용자
     */
    public User findByUserId (String userId)
    {
        return userRepository.findByUserId(userId);
    }

    /**
     * 사용자 이름에 해당하는 사용자 목록을 조회한다.
     * @param userNm 조회할 사용자의 이름
     * @return 사용자 이름에 해당하는 사용자 목록
     */
    public List<User> findByUserNm (String userNm)
    {
        return userRepository.findByUserNm(userNm);
    }

    /**
     * 특정 문자열이 이름에 포함된 사용자 목록을 조회한다.
     * @param userNm 검색할 문자열
     * @return 특정 문자열이 이름에 포함된 사용자 목록
     */
    public List<User> findByUserNmLike (String userNm)
    {
        return userRepository.findByUserNmLike(userNm);
    }

    /**
     * 사용자 정보를 수정한다.
     * @param userVO 수정할 사용자 정보
     * @return 수정된 사용자 정보
     */
    public User save (User userVO)
    {
        userRepository.save(userVO);
        return userVO;
    }

    /**
     * 사용자 삭제
     * @param userId 삭제할 사용자의 아이디
     */
    public void deleteByUserId (String userId)
    {
        User userVO = findByUserId(userId);
        if (!userVO.equals(null))
        {
            userRepository.delete(userVO);
        }
    }
}
