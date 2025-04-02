package com.moviment.service;

import com.moviment.dto.ListAndPage;
import com.moviment.dto.MovieVOAndReview;
import com.moviment.dto.SearchResult;
import com.moviment.dto.UserSessionDTO;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import org.springframework.ui.Model;

import java.util.List;

public interface MovieService {
    SearchResult searchMovies(String keyword, Model model);
    MovieVO searchDetail(int id, Model model);
    List<ReviewVO> searchReview(int id);
    void addReview(UserSessionDTO user, ReviewVO review);
    void deleteReview(ReviewVO review);
    void patchReview(ReviewVO review);
    // 메인 화면 내 현재상영작, 개봉예정작, 명예의전당 조회
    List<MovieVO> getListOfMovieListByType(String movieListTypeInMain, int userPage);
    // 검색을 통한 영화 조회
    ListAndPage searchMovies(String keyword, int userPage);
    // 상세 페이지 및 댓글 조회
    MovieVO searchDetail(int id);
}
