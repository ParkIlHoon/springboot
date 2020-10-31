package com.ilhoon.demo.controller;

import com.ilhoon.demo.service.UserService;
import com.ilhoon.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 전체 사용자 조회
     *
     * @return 전체 사용자 리스트
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.findAll();

        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    /**
     * 사용자 조회
     *
     * @param userNm 조회 대상 사용자 이름
     * @return 사용자 목록
     */
    @GetMapping(params = "method=name", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<User>> getUserListByNm(@RequestParam(value = "userNm") String userNm) {
        List<User> userList = userService.findByUserNmLike(userNm);

        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    /**
     * 특정 사용자 조회
     *
     * @param userId 조회 대상 사용자 아이디
     * @return 특정 사용자 정보
     */
    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
        User user = userService.findByUserId(userId);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * 사용자 정보 수정
     *
     * @param userId 대상자 아이디
     * @param userVO 수정 값
     * @return 수정 완료된 사용자 정보
     */
    @PutMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> updateUser(@PathVariable("userId") String userId, User userVO) {
        User user = userService.save(userVO);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * 사용자 추가
     *
     * @param userVO
     * @return
     */
    @PostMapping
    public ResponseEntity<User> insertUser(User userVO) {
        User user = userService.save(userVO);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * 사용자 삭제
     *
     * @param userId
     */
    @DeleteMapping(value = "/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteByUserId(userId);
    }
}
