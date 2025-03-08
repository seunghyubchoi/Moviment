package com.moviment.repository;

import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;

public interface MovieRepository {
    MovieVO findMovieById(int id);
    void saveMovie(MovieVO movie);
    void addReview(UserVO user, ReviewVO review);
}
