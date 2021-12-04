package com.zerock.board.repository;

import java.util.List;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.MemberInfo;
import com.zerock.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        //1부터 100까지 반복문 돌기 위해 IntStream
        IntStream.rangeClosed(1, 100).forEach(i -> {
            //1부터 100까지의 임의의 번호
            long bno = (long) (Math.random() * 100) + 1;
            //vo에 값 set 해주는 것처럼 빌더패턴 형식으로 값 세팅
            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply ...... " + i)
                    .board(board)
                    .replyer("guest")
                    .build();
            //JpaRepository 를 통해 insert 실행
            replyRepository.save(reply);
        });
    }

    @Test
    public void readReply1(){
        Optional<Reply> result = replyRepository.findById(100L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    @Test
    public void testListByBoard(){
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(100L).build());

        replyList.forEach(reply -> System.out.println(reply));

    }
}
