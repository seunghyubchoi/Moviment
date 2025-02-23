package com.moviment.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 로그인 관련 예외 처리
     * @param e
     * @param redirectAttributes
     * @return
     */
    @ExceptionHandler(LoginException.class)
    public String handleLoginException(LoginException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", e.getMessage());
        return "redirect:/";
    }
}
