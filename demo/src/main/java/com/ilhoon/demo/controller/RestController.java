package com.ilhoon.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @RestController 란?
 * @Controller + @ResponseBody
 * JSON 또는 XML 형태의 데이터 반환시 사용. API 서버로 활용시 많이 사용한다.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController
{

    @RequestMapping(value = "/rest/testvalue", method = RequestMethod.GET)
    public String getTestValue ()
    {
        return "레스트컨트롤러";
    }
}
