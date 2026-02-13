package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BoardListDTO {
    private Long id;
    private String title;
    private String writer;
    private int viewCount;
    private LocalDateTime createdAt;

    public BoardListDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.viewCount = board.getViewCount();
        this.createdAt = board.getCreatedAt();
    }
}