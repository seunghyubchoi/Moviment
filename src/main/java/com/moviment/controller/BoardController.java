package com.moviment.controller;

import com.moviment.model.BoardVO;
import com.moviment.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BoardController {

    private BoardService boardService;

    @Autowired
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public String getboardList(Model model) {
        List<BoardVO> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board";
    }

}
