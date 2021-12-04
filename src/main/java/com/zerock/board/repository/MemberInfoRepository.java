package com.zerock.board.repository;

import com.zerock.board.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

//모든 Jpa 기능을 사용하고 싶을 떄는 JpaRepository 인터페이스 이용.
// 스프링이 내부적으로 해당 인터페이스에 맞는 코드를 생성하는 방식 이용.
//JpaRepository<클래스타입,Id>
//JpaRepository 선언만으로 스프링의 빈(Bean)으로 등록
public interface MemberInfoRepository extends JpaRepository<MemberInfo,String> {
}
