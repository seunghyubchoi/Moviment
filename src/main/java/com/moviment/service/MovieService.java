package com.moviment.service;

import com.moviment.model.MovieVO;
import org.springframework.ui.Model;

import java.util.List;

public interface MovieService {
    List<MovieVO> searchMovies(String keyword, Model model);
}
