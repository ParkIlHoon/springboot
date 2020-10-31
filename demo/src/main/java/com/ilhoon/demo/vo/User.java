package com.ilhoon.demo.vo;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {
    @Id
    @Column
    private String userId;

    @Column
    private String userNm;

    @Column
    private String userMail;

}
