package com.moviment.controller;

import com.moviment.model.UserVO;
import com.moviment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @PostMapping(value= "/login")
    public void login() {
        System.out.println("로그인 테스트");
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/register")
    public String register(UserVO user) {
        userService.saveUser(user);
        return "redirect:/login";
    }
}
