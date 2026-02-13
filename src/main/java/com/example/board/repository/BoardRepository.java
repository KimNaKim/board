package com.example.board.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.board.entity.Board;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    // 저장하기
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    // 하나만 조회하기
    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

    // 전체 목록 조회하기
    public List<Board> findAll(int page, int size) {
        return em.createQuery("select b from Board b order by b.id desc", Board.class)
                .setFirstResult(page * size) // 시작 위치
                .setMaxResults(size) // 가져올 개수
                .getResultList();
    }

    // 전체 게시글 수 나타내기
    public long count() {
        return em.createQuery("select count(b) from Board b", Long.class)
                .getSingleResult();
    }

    // 검색과 페이징을 동시에 하기
    public List<Board> findBySearch(String searchType, String keyword, int page, int size) {
        String jpql = createSearchJpql(searchType);

        return em.createQuery(jpql, Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    // 검색 결과 수
    public long countBySearch(String searchType, String keyword) {
        String jpql = createCountJpql(searchType);
        return em.createQuery(jpql, Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();
    }

    // -----------private 헬퍼 메서드-------------

    private String createCountJpql(String searchType) {
        String where = createWhereClause(searchType);
        return "SELECT COUNT(b) FROM Board b WHERE " + where;
    }

    private String createSearchJpql(String searchType) {
        String where = createWhereClause(searchType);
        return "SELECT b FROM Board b WHERE " + where + " ORDER BY b.id DESC";
    }

    private String createWhereClause(String searchType) {
        return switch (searchType) {
            case "title" -> "LOWER(b.title) LIKE LOWER(:keyword)"; // Lower()로 대소문자 무관 검색
            case "writer" -> "LOWER(b.writer) LIKE LOWER(:keyword)";
            case "titleContent" -> "LOWER(b.title) LIKE LOWER(:keyword) OR LOWER(b.content) LIKE LOWER(:keyword)";
            default -> "LOWER(b.title) LIKE LOWER(:keyword)";
        };
    }
}
