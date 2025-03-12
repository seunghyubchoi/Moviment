package com.moviment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class LayoutController {

    /**
     * index 페이지 이동
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "message", required = false, defaultValue = "") String message, Model model, HttpSession session) {
        if(!message.isEmpty()) {
            model.addAttribute("message", message);
            log.debug((String) model.getAttribute("message"));
        }

        if(session.getAttribute("user") != null) {
            return "redirect:/moviment";
        }

        return "index";
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
     * 화면 이동
     * @param contentPage
     * @param content
     * @param model
     * @return
     */
    @GetMapping({"/moviment","/moviment/{contentPage}"})
    public String moviment(@PathVariable(name= "contentPage", required = false) String contentPage,
                           @RequestParam(name = "contentPage", defaultValue = "main") String content,
                           Model model) {
        String finalContentPage = (contentPage != null) ? contentPage : content;
        model.addAttribute("contentPage", "/WEB-INF/views/" +  finalContentPage + ".jsp");
        return "moviment";
    }

    /**
     * 메뉴 선택 시 페이지 이동
     * @param content
     * @return
     */
    @GetMapping("/loadContent")
    public String loadContent(String content) {
        return content;
    }
}
