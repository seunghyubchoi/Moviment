package com.moviment.controller;

import com.moviment.dto.ListAndPage;
import com.moviment.dto.MovieVOAndReview;
import com.moviment.dto.SearchResult;
import com.moviment.dto.UserSessionDTO;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;
import com.moviment.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ResponseBody
    @GetMapping("/movies/{keyword}")
    public ResponseEntity<?> search(@PathVariable String keyword,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int userPage) {
        ListAndPage list = movieService.searchMovies(keyword, userPage);
        if(list == null || list.getMovieVOList().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("응답 데이터가 없습니다.");
        }
        return ResponseEntity.ok(list);
    }

    @ResponseBody
    @GetMapping("/movies/detail/{id}")
    public ResponseEntity<?> searchDetail(@PathVariable int id,
                                          @RequestParam(name = "page", defaultValue = "1") int userPage,
                                          @RequestParam(name = "keyword") String keyword) {
        MovieVO movieDetail = movieService.searchDetail(id);
        List<ReviewVO> reviewList = movieService.searchReview(id);
        return ResponseEntity.ok(new MovieVOAndReview(movieDetail, reviewList, userPage, keyword));
    }

    @ResponseBody
    @PostMapping("/review")
    public ResponseEntity<?> addReview(HttpSession session, @RequestBody ReviewVO review) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        log.info("user : {}", user);
        movieService.addReview(user, review);
        return ResponseEntity.ok("review add success");
    }

    @ResponseBody
    @DeleteMapping("/review")
    public ResponseEntity<?> deleteReview(@RequestBody ReviewVO review) {
        movieService.deleteReview(review);
        return ResponseEntity.ok("review delete success");
    }

    @ResponseBody
    @PatchMapping("/review")
    public ResponseEntity<?> patchReview(@RequestBody ReviewVO review) {
        movieService.patchReview(review);
        return ResponseEntity.ok("review patch success");
    }

    // 메인페이지 현재상영작, 개봉예정작, 명예의전당 조회
    @ResponseBody
    @GetMapping("/movies/main/{movieListTypeInMain}")
    public ResponseEntity<?> getListOfNowPlaying(@PathVariable String movieListTypeInMain, @RequestParam(name = "page", defaultValue = "1") int userPage) {
        List<MovieVO> list = movieService.getListOfMovieListByType(movieListTypeInMain, userPage);
        if(list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("응답 데이터가 없습니다.");
        }
        return ResponseEntity.ok(list); // 200 OK
    }
}
