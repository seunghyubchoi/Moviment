package com.moviment.controller;

import com.moviment.model.UserVO;
import com.moviment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String index(@ModelAttribute("message") String message, Model model) {
        if(message != null && !message.isEmpty()) {
            model.addAttribute("message", message);
        }
        System.out.println(model.getAttribute("message"));
        return "index";
    }

    /**
     * 로그인
     * @param user
     */
    @PostMapping(value= "/login")
    public String login(UserVO user, Model model, BindingResult result, HttpSession session) {
        UserVO loginUser = userService.getUser(user, model, result);
        // 로그인 성공 시 세션에 사용자 정보 저장
        session.setAttribute("user", loginUser);
        session.setAttribute("userId", loginUser.getId());
        session.setAttribute("userEmail", loginUser.getEmail());
        session.setAttribute("userName", loginUser.getUsername());
        session.setAttribute("userRole", loginUser.getRole());
        session.setAttribute("userAuth", loginUser.getAuth_provider());
        System.out.println("session : " + session.toString());
        return "redirect:/layout";
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
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserVO user, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        System.out.println("register 유효성 검사 시작");
        if(result.hasErrors()) {
            System.out.println("유효성 검사 실패 : " + result.getAllErrors());
            // 현재 페이지에서 에러 메세지 출력할 것이므로 redirectAttributes -> Model 로 변경
            model.addAttribute("message", result.getAllErrors().get(0).getDefaultMessage());
            return "register";
        }

        if(user.getAuth_provider() == null) {
            user.setAuth_provider("LOCAL");
        }
        if(user.getRole() == null) {
            user.setRole("USER");
        }

        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다! 가입한 아이디로 로그인하시기 바랍니다.");
        return "redirect:/";
    }

}
