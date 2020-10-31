package com.ilhoon.demo.repository;

import com.ilhoon.demo.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserId(String userId);

    public List<User> findByUserNm(String userNm);

    public List<User> findByUserNmLike(String userNm);
}
