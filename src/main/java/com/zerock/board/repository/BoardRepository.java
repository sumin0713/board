package com.zerock.board.repository;


import java.util.List;
import com.zerock.board.entity.Board;
import com.zerock.board.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long> , SearchBoardRepository {

    //한개의 로우(Object) 내에 Object[]로 나옴
    @Query("select b,w from Board b left join b.writer w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b, r from Board b left join Reply r ON r.board = b Where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value = "select b,w,count(r) " +
            "from Board b " +
            "Left join b.writer w " +
            "left join Reply r On r.board = b " +
            "group by b",
            countQuery ="Select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("select b,w, count(r) from Board b Left join b.writer w Left outer join Reply r on r.board = b where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}