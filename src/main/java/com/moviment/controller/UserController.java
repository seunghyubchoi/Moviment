package com.moviment.controller;

import com.moviment.dto.UserInfoDTO;
import com.moviment.dto.UserSessionDTO;
import com.moviment.model.UserVO;
import com.moviment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@Controller
public class UserController {

    private UserService userService;
    private MessageSource messageSource;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인
     * @param user
     */
    @PostMapping(value= "/login")
    public String login(UserVO user, Model model, BindingResult result, HttpSession session) {
        UserVO loginUser = userService.getUser(user, model, result);

        UserSessionDTO userSessionDTO = new UserSessionDTO(
                loginUser.getId()
                ,loginUser.getEmail()
                ,loginUser.getUsername()
                ,loginUser.getAuth_provider()
                ,loginUser.getRole()
        );
        log.debug("userInfoDTO: {}", userSessionDTO);

        session.setAttribute("user", userSessionDTO);
        return "redirect:/moviment";
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
    public String register(@Valid @ModelAttribute UserVO user, BindingResult result, RedirectAttributes redirectAttributes, Model model, Locale locale) {
        if(result.hasErrors()) {
            log.debug("유효성 검사 실패 : " + result.getAllErrors());
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
        String message = messageSource.getMessage("alert.register.success", new Object[]{user.getUsername()}, locale);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }

    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@Valid @ModelAttribute("user") UserInfoDTO user,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 Locale locale) {

        if(result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("contentPage", "/WEB-INF/views/userInfo.jsp");
            return "moviment";
        }

        userService.updateUserInfo(user);
        String message = messageSource.getMessage("alert.register.passwordChanged", null, locale);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:moviment";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
