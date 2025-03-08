package com.moviment.repository;

import com.moviment.mapper.MovieMapper;
import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieMybatisRepository implements MovieRepository {

    private MovieMapper movieMapper;

    @Autowired
    public void setMovieMapper(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieVO findMovieById(int id) {
        return movieMapper.findMovieById(id);
    }

    @Override
    public void saveMovie(MovieVO movie) {
        movieMapper.saveMovie(movie);
    }

    @Override
    public void addReview(UserVO user, ReviewVO review) {
        movieMapper.addReview(user, review);
    }
}
