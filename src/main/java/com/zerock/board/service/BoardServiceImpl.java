package com.zerock.board.service;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDTO;
import com.zerock.board.dto.PageResultDTO;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.MemberInfo;
import com.zerock.board.repository.BoardRepository;
import com.zerock.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService{

    private final BoardRepository repository; // 자동 주입 final

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto){
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();

    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO);

        Function<Object[],BoardDTO> fn = (en->entityToDTO((Board)en[0],(MemberInfo) en[1],(Long) en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result,fn);

    }

    @Override
    public BoardDTO get(Long bno){
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;
        System.out.println("================");
        System.out.println(Arrays.toString(arr));
        return entityToDTO((Board)arr[0],(MemberInfo) arr[1],(Long) arr[2]);
    }

    //댓글삭제와 게시물 삭제를 하나의 트랜잭션으로 관리하기 위해서 해당 어노테이션 사용
    @Transactional
    @Override
    public void removeWithReplies(Long bno){

        //댓글부터 삭제
        replyRepository.deleteByBno(bno);
        repository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO){
        Board board = repository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }
}
