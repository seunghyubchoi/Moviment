package com.moviment.mapper;

import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import com.moviment.model.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    MovieVO findMovieById(int id);
    void saveMovie(MovieVO movie);
    void addReview(@Param("user") UserVO user, @Param("review") ReviewVO review);
    List<ReviewVO> searchReview(int id);
    void deleteReview(@Param("review") ReviewVO review);
    void patchReview(@Param("review")ReviewVO review);
}
