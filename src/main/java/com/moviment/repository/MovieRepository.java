package com.moviment.repository;

import com.moviment.model.MovieVO;

public interface MovieRepository {
    MovieVO findMovieById(String id);
    void saveMovie(MovieVO movie);
}
