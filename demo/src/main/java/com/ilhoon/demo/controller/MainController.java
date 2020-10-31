package com.ilhoon.demo.controller;

import com.ilhoon.demo.service.UserService;
import com.ilhoon.demo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String main() {
        return "index.html";
    }

    @RequestMapping("/jsp")
    public ModelAndView jsp() {
        ModelAndView model = new ModelAndView("main");

        User user = userService.findByUserId("1hoon");

        model.addObject("testvalue", "안녕!");
        model.addObject("user", user.toString());

        return model;
    }
}
