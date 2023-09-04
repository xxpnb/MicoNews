package com.crab.controller;

import com.crab.service.TestService;
import com.crab.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xxp
 * @version sota；
 */
@RestController
@RequestMapping("/news-test")
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private JwtHelper jwtHelper;

    @GetMapping("/hello1")
    public String hi(){
       return testService.say();
    }

    @GetMapping("/hello2")
    public String hi2(){
        String token = jwtHelper.createToken(1l);
        return token;

    }

}
