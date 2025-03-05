package com.moviment.mapper;

import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {
    MovieVO findMovieById(int id);
    void saveMovie(MovieVO movie);
    void addReview(ReviewVO review);
}
