package com.moviment.controller;

import com.moviment.exception.MovieException;
import com.moviment.model.MovieVO;
import com.moviment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<MovieVO> search(@ModelAttribute("message") String message, String keyword, Model model) {
        if(message != null && !message.isEmpty()) {
            //model.addAttribute("message", message);
            throw new MovieException(message);
        }

        List<MovieVO> movieList = movieService.searchMovies(keyword, model);
        model.addAttribute("movieList", movieList);
        model.addAttribute("keyword", keyword);
        return movieList;
    }

    @GetMapping("/board")
    public void boardTest() {
        System.out.println("board 테스트!!!!");
    }
}
