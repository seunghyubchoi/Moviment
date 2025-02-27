package com.moviment.exception;

import org.apache.ibatis.logging.LogException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

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

    /**
     * TMDB API 관련 예외 처리
     * @param e
     * @param redirectAttributes
     * @return
     */
    @ExceptionHandler(MovieException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMovieException(MovieException e, RedirectAttributes redirectAttributes) {
        //redirectAttributes.addFlashAttribute("message", e.getMessage());
        //return "redirect:/api/search";
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        return errorResponse;
    }
}
