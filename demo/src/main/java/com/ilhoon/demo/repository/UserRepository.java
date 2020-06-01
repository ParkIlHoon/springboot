package com.ilhoon.demo.repository;

import com.ilhoon.demo.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    public List<User> findByUserId(String userId);
}
