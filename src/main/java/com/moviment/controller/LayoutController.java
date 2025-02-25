package com.moviment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutController {
    /**
     * 모든 페이지 포함 jsp
     * @return
     */
    @GetMapping("/layout")
    public String layout() {
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
