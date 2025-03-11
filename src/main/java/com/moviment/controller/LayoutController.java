package com.moviment.controller;

import com.moviment.model.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {
    /**
     * 모든 페이지 포함 jsp
     * @return
     */
    @GetMapping("/layout")
    public String layout(Model model) {
        model.addAttribute("contentPage", "main.jsp");
        return "layout";
    }

    /**
     * 회원정보수정
     * @param model
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(Model model) {
        model.addAttribute("contentPage", "/WEB-INF/views/userInfo.jsp");
        return "layout";
    }

    /**
     * 즐겨찾기
     * @param model
     * @return
     */
    @GetMapping("/userFavorite")
    public String userFavorite(Model model) {
        model.addAttribute("contentPage", "/WEB-INF/views/userFavorite.jsp");
        return "layout";
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
