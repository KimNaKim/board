package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardWriteDTO {
    private String title;
    private String content;
    private String writer;
    private MultipartFile file; // 업로드 파일

    public Board toEntity(String fileName, String originalFileName) {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .writer(this.writer)
                .fileName(fileName)
                .originalFileName(originalFileName)
                .build();
    }
}