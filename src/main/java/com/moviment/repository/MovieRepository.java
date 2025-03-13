package com.moviment.repository;

import com.moviment.dto.UserSessionDTO;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;

import java.util.List;

public interface MovieRepository {
    MovieVO findMovieById(int id);
    void saveMovie(MovieVO movie);
    void addReview(UserSessionDTO user, ReviewVO review);
    List<ReviewVO> searchReview(int id);
    void deleteReview(ReviewVO review);
    void patchReview(ReviewVO review);
}
