package com.moviment.repository;

import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;

public interface MovieRepository {
    MovieVO findMovieById(int id);
    void saveMovie(MovieVO movie);
    void addReview(String userId, ReviewVO review);
}
