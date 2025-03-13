package com.moviment.controller;

import com.moviment.dto.SearchResult;
import com.moviment.dto.UserSessionDTO;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;
import com.moviment.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
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
        List<ReviewVO> reviewList = movieService.searchReview(id);

        model.addAttribute("movieDetail", movieDetail); // 영화 상세 페이지 모델 내 추가

        if(reviewList != null) {
            model.addAttribute("reviewList", reviewList); // 댓글 있는 경우 추가
        }
        return "searchResults";
    }

    @PostMapping("/review")
    public String addReview(HttpSession session, @RequestBody ReviewVO review, Model model) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        log.info("user : {}", user);
        movieService.addReview(user, review);
        return "searchResults";
    }

    @DeleteMapping("/review")
    public String deleteReview(@RequestBody ReviewVO review) {
        movieService.deleteReview(review);
        return "searchResults";
    }

    @PatchMapping("/review")
    public String patchReview(@RequestBody ReviewVO review) {
        movieService.patchReview(review);
        return "searchResults";
    }
}
