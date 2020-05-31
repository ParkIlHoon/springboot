package com.ilhoon.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController
{
    @RequestMapping("/")
    public String main ()
    {
        return "index.html";
    }

    @RequestMapping("/jsp")
    public ModelAndView jsp ()
    {
        ModelAndView model = new ModelAndView("main");

        model.addObject("testvalue", "안녕!");

        return model;
    }
}
