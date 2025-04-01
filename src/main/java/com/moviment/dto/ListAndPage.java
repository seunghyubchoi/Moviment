package com.moviment.dto;

import com.moviment.model.MovieVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ListAndPage {
    private List<MovieVO> movieVOList; // 현재 페이지의 영화 목록
    private int totalPages; // 전체 페이지수 끝자락 갈때 쓸 용도
    private int pageGroup; // 페이지 그룹 1~5..
    private int currentPage; // 현재 페이지 번호 표기용
    private int firstPage; // 그룹 첫번째 번호
    private int lastPage; // 그룹 마지막 번호
    private String keyword;

    public boolean isEmpty() {
        return movieVOList == null || movieVOList.isEmpty();
    }
}
