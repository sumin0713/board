package com.zerock.board.repository;

import java.util.List;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.MemberInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        
        //1부터 100까지 반복문 돌기 위해 IntStream
        IntStream.rangeClosed(1,100).forEach(i->{

            //vo에 값 set 해주는 것처럼 빌더패턴 형식으로 값 세팅
            MemberInfo memberInfo = MemberInfo.builder()
                    .email("user"+i+"@aaa.com")
                    .build();
            Board board = Board.builder()
                    .title("Title ... "+i)
                    .content("Contents ... "+i)
                    .writer(memberInfo)
                    .build();
            //JpaRepository 를 통해 insert 실행
            boardRepository.save(board);
        });
    }

    @Transactional //메소드를 하나의 트랜잭션처럼 사용
    @Test
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;

        System.out.println("==========================");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").ascending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row ->{
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });
        //page 타입은 실제 페이지 처리에 필요한 전체 데이터의 개수를 가져오는 쿼리 같이 처리
        System.out.println(result.getTotalPages());
        System.out.println(result.getTotalElements());
    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);
        System.out.println(result);

        System.out.println("-------------------");
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.searchPage("t","1",pageable);
    }
}
