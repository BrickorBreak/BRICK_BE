package com.brick.repository;

import com.brick.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    // 로그인 아이디(String id)로 조회
    Optional<AuthUser> findById(String id);

    // 전화번호 중복 체크
    Optional<AuthUser> findByPhone(String phone);
}
