package com.moviment.repository;

import com.moviment.mapper.BoardMapper;
import com.moviment.model.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardMybatisRepository implements BoardRepository {

    private BoardMapper boardMapper;

    @Autowired
    public void setBoardMapper(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<BoardVO> getBoardList() {
        return boardMapper.getBoardList();
    }
}
