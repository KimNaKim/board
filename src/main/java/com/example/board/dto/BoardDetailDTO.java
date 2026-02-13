package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BoardDetailDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String fileName;
    private String originalFileName;
    private int viewCount;
    private LocalDateTime createdAt;

    public BoardDetailDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getWriter();
        this.fileName = board.getFileName();
        this.originalFileName = board.getOriginalFileName();
        this.viewCount = board.getViewCount();
        this.createdAt = board.getCreatedAt();
    }
}