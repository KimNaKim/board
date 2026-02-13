package com.example.board;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DataInit implements CommandLineRunner {
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void run(String... args) {
        for (int i = 1; i <= 23; i++) {
            Board board = Board.builder()
                    .title("테스트 게시글 " + i)
                    .content("테스트 내용입니다. " + i + "번째 게시글입니다.")
                    .writer(i %
                            2 == 0 ? "홍길동" : "김철수")
                    .build();
            boardRepository.save(board);
        }
    }
}