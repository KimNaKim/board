package com.example.board.dto;

import lombok.Getter;
import java.util.List;

//JpaRepository 없이 직접 EntityManager로 페이징하기 때문에 생성하는 DTO
@Getter
public class PageDTO<T> {
    private List<T> content;
    private int page;
    private int totalPages; // 전체 페이지를 계산해 담는 변수
    // 현재 페이지 게시글 목록
    // 현재 페이지 번호 (0부터 시작)
    // 전체 페이지 수
    private long totalElements; // 전체 게시글 수
    private boolean first;
    // 첫 페이지 여부
    private boolean last;
    private int startPage; // 페이지 네비게이션 바에서 보여줄 범위위
    private int endPage;

    // 마지막 페이지 여부
    // 페이지 네비게이션 시작 번호
    // 페이지 네비게이션 끝 번호
    public PageDTO(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = (page == 0);
        this.last = (page >= totalPages - 1);
        // 페이지 네비게이션: 한 번에 5개씩 보여주기
        // 예: 현재 7페이지 → [5] [6] [7] [8] [9]
        int blockSize = 5;
        this.startPage = (page / blockSize) * blockSize;
        this.endPage = Math.min(startPage + blockSize - 1, totalPages - 1);
    }
}