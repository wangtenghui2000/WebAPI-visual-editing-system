package com.activiti.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/api/user")
    public String say(){
        return "Hello,I am SpringBoot!";
    }
}


