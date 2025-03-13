package com.moviment.service;

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
}
