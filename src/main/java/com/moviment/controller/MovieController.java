package com.moviment.controller;

import com.moviment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/search")
    public void searchTest(String keyword) {
        System.out.println("search 테스트!!!!");
        movieService.search(keyword);
    }

    @GetMapping("/board")
    public void boardTest() {
        System.out.println("board 테스트!!!!");
    }
}
