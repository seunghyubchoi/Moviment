package com.moviment.dto;

import com.moviment.model.MovieVO;
import com.moviment.model.ReviewVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MovieVOAndReview {
    private MovieVO movieVO; // 현재 페이지의 영화 목록
    private List<ReviewVO> reviewVOList; // 댓글 목록
    private int targetPage; // 상세 페이지 접근 시 페이지
    private String keyword; // 상세 페이지 접근 시 키워드
}
