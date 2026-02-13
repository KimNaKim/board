package com.example.board.controller;

import com.example.board.dto.BoardDetailDTO;
import com.example.board.dto.BoardWriteDTO;
import com.example.board.dto.PageDTO;
import com.example.board.dto.BoardListDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    /**
     * 
     * 게시글 목록 (검색 + 페이징)
     * GET /board/list?page=0&searchType=title&keyword=검색어
     */
    @GetMapping("/list")
    public String list(
            @RequestParam(defaultValue = "") String searchType,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        int size = 5; // 한 페이지에 5개씩
        PageDTO<BoardListDTO> pageDTO = boardService.findAll(searchType, keyword,
                page, size);
        model.addAttribute("boards", pageDTO);
        model.addAttribute("searchType", searchType);
        model.addAttribute("keyword", keyword);
        return "board/list";
    }

    /**
     * 
     * 게시글 등록 폼
     * GET /board/write
     */
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    /**
     * 
     * 게시글 등록 처리
     * POST /board/write
     */
    @PostMapping("/write")
    public String write(BoardWriteDTO dto) throws IOException {
        boardService.save(dto);
        return "redirect:/board/list";
    }

    /**
     * *
     * 게시글 상세보기
     * GET /board/detail/1
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        BoardDetailDTO board = boardService.findById(id);
        model.addAttribute("board", board);
        return "board/detail";
    }
}