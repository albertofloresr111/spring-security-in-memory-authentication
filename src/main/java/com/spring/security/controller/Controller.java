package com.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @GetMapping("/admin")
    public String admin(){
        return "Inside of Admin";
    }

    @GetMapping("/greetings")
    public String sayGreetings(){
        return "Greetings!!!";
    }

    @GetMapping("/test1")
    public String test1(){
        return "Inside of test 1";
    }

    @GetMapping("/test2")
    public String test2(){
        return "Inside of test2";
    }
}