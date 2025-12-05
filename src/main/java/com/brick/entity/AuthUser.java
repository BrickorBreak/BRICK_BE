package com.brick.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "auth_users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId; //PK (DB 자동 생성됨 !)

    @Column(nullable = false, unique = true, length = 50)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Column(nullable = false, length = 10)
    private String gender;

}
