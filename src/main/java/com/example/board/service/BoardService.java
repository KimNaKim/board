package com.example.board.service;

import com.example.board.dto.BoardDetailDTO;
import com.example.board.dto.BoardListDTO;
import com.example.board.dto.BoardWriteDTO;
import com.example.board.dto.PageDTO;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final FileService fileService;

    /**
     * 
     * 게시글 등록 (파일 업로드 포함)
     */
    @Transactional
    public Long save(BoardWriteDTO dto) throws IOException {
        // 1. 파일 저장
        MultipartFile file = dto.getFile();
        String savedFileName = fileService.uploadFile(file);
        // 2. 원본 파일명 (파일이 없으면 null)
        String originalFileName = null;
        if (file != null && !file.isEmpty()) {
            originalFileName = file.getOriginalFilename();
        }
        // 3. DTO → Entity 변환 후 저장
        Board board = dto.toEntity(savedFileName, originalFileName);
        boardRepository.save(board);
        return board.getId();
    }

    /**
     * 
     * 게시글 목록 조회 (검색 + 페이징)
     */
    public PageDTO<BoardListDTO> findAll(String searchType, String keyword, int page, int size) {
        List<Board> boards;
        long totalCount;
        // 검색어가 있으면 검색, 없으면 전체 조회
        if (keyword == null || keyword.trim().isEmpty()) {
            boards = boardRepository.findAll(page, size);
            totalCount = boardRepository.count();
        } else {
            boards = boardRepository.findBySearch(searchType, keyword.trim(),
                    page, size);
            totalCount = boardRepository.countBySearch(searchType,
                    keyword.trim());
        }
        // Entity → DTO 변환
        List<BoardListDTO> dtoList = boards.stream()
                .map(BoardListDTO::new)
                .toList();
        return new PageDTO<>(dtoList, page, size, totalCount);
    }

    /**
     * 
     * 게시글 상세 조회 (조회수 증가)
     */
    @Transactional
    public BoardDetailDTO findById(Long id) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id);
        }
        // 조회수 증가 → @Transactional이므로 Dirty Checking으로 자동 UPDATE
        board.increaseViewCount();
        return new BoardDetailDTO(board);
    }
}