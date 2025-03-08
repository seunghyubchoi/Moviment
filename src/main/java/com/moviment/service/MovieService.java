package com.moviment.service;

import com.moviment.dto.SearchResult;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;
import org.springframework.ui.Model;

public interface MovieService {
    SearchResult searchMovies(String keyword, Model model);
    MovieVO searchDetail(int id, Model model);
    void addReview(UserVO user, ReviewVO review);
}
