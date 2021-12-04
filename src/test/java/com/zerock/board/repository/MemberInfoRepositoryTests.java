package com.zerock.board.repository;

import com.zerock.board.entity.MemberInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberInfoRepositoryTests {

    @Autowired
    private MemberInfoRepository memberInfoRepository;

    @Test
    public void insertMemberInfo(){

        //1부터 100까지 반복문 돌기 위해 IntStream
        IntStream.rangeClosed(1,100).forEach(i->{

            //vo에 값 set 해주는 것처럼 빌더패턴 형식으로 값 세팅
            MemberInfo memberInfo = MemberInfo.builder()
                    .email("user"+i+"@aaa.com")
                    .password("1111")
                    .name("USER"+i)
                    .build();

            //JpaRepository 를 통해 insert 실행
            memberInfoRepository.save(memberInfo);
        });
    }
}
