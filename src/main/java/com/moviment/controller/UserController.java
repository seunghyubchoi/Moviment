package com.moviment.controller;

import com.moviment.model.UserVO;
import com.moviment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * index 페이지 이동
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 로그인
     * @param user
     */
    @PostMapping(value= "/login")
    public void login(UserVO user) {
        userService.getUser(user);
    }

    /**
     * 회원가입 페이지 이동
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserVO user, BindingResult result) {
        System.out.println("register 유효성 검사 시작");
        /*
        if(result.hasErrors()) {
            System.out.println("유효성 검사 실패 : " + result.getAllErrors());
            return "register";
        }
        */

        if(user.getAuth_provider() == null) {
            user.setAuth_provider("LOCAL");
        }
        if(user.getRole() == null) {
            user.setRole("USER");
        }

        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/";
    }

}
