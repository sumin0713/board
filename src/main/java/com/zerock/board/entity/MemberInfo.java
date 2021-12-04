package com.zerock.board.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table
public class MemberInfo extends BaseEntity{

    @Id
    private String email;

    private String password;

    private String name;


}
