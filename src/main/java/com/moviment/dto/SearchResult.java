package com.moviment.dto;

import com.moviment.model.MovieVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SearchResult {
    private List<MovieVO> movieVOList;
    private int totalPages;
}
