package com.moviment.repository;

import com.moviment.model.BoardVO;

import java.util.List;

public interface BoardRepository {
    List<BoardVO> getBoardList();
}
