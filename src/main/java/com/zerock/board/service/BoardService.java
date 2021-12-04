package com.zerock.board.service;

import com.zerock.board.dto.BoardDTO;
import com.zerock.board.dto.PageRequestDTO;
import com.zerock.board.dto.PageResultDTO;
import com.zerock.board.entity.Board;
import com.zerock.board.entity.MemberInfo;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO); //목록처리

    void removeWithReplies(Long bno); //삭제 기능

    default Board dtoToEntity(BoardDTO dto){
        MemberInfo member = MemberInfo.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder().bno(dto.getBno()).title(dto.getTitle()).content(dto.getContent()).writer(member).build();

        return board;
    }

    //BoardService 인터페이스에 추가하는 entityToDTO()
    default BoardDTO entityToDTO(Board board, MemberInfo member, Long replyCount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
        return boardDTO;
    }

    BoardDTO get(Long bno);

    void modify(BoardDTO boardDTO);
}
