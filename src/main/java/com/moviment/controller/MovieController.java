package com.moviment.controller;

import com.moviment.dto.SearchResult;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.service.MovieService;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public String search(@ModelAttribute("message") String message, @RequestParam(defaultValue = "1") int page, String keyword, Model model) {
        SearchResult searchResult = movieService.searchMovies(keyword, model);

        List<MovieVO> movieList = searchResult.getMovieVOList();
        int totalPages = searchResult.getTotalPages(); // 전체 페이지 수

        // 20개씩 끊어서 보여주기
        int startIndex = (page - 1) * 20; // List 인덱스 start
        int endIndex = Math.min(startIndex + 20, movieList.size()); // List 인덱스 end

        List<MovieVO> paginatedMovieList = movieList.subList(startIndex, endIndex); // start 부터 end 까지의 List


        model.addAttribute("movieList", paginatedMovieList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPages);
        return "searchResults";
    }

    @GetMapping("/movies/{id}")
    public String searchDetail(@PathVariable int id, Model model) {
        MovieVO movieDetail = movieService.searchDetail(id, model);
        System.out.println("movieDetail = " + movieDetail);
        model.addAttribute("movieDetail", movieDetail);
        System.out.println(model);

        return "searchResults";
    }

    @PostMapping("/addReview")
    public String addReview(@RequestBody ReviewVO review, Model model) {
        movieService.addReview(review);
        return "searchResults";
    }
}
