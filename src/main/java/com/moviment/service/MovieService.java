package com.moviment.service;

import com.moviment.dto.SearchResult;
import com.moviment.model.MovieVO;
import org.springframework.ui.Model;

import java.util.List;

public interface MovieService {
    SearchResult searchMovies(String keyword, Model model);
}
