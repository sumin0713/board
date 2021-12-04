package com.zerock.board.repository;

import java.util.List;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Modifying //데이터에 변경이 일어나는 INSERT, UPDATE, DELETE, DDL 에서 사용합니다.
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);

    //게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);
}
