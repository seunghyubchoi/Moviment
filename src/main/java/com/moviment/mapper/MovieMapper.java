package com.moviment.mapper;

import com.moviment.model.MovieVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {
    MovieVO findMovieById(String id);
    void saveMovie(MovieVO movie);
}
