package com.moviment.controller;

import com.moviment.model.UserVO;
import com.moviment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    /**
     * 회원가입
     * @param user
     * @param result
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserVO user, BindingResult result, HttpSession session, Model model) {
        System.out.println("register 유효성 검사 시작");
        if(result.hasErrors()) {
            System.out.println("유효성 검사 실패 : " + result.getAllErrors());
            //redirectAttributes.addFlashAttribute("errorMessage", result.getAllErrors().get(0).getDefaultMessage());
            // 현재 페이지에서 에러 메세지 출력할 것이므로 redirectAttributes -> Model 로 변경
            model.addAttribute("errorMessage", result.getAllErrors().get(0).getDefaultMessage());
            return "register";
        }

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
