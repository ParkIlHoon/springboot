package com.ilhoon.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
public class RestController
{

    @RequestMapping(value = "/rest/testvalue", method = RequestMethod.GET)
    public String getTestValue ()
    {
        return "레스트컨트롤러";
    }
}
