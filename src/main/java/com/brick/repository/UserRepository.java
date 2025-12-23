package com.brick.repository;

import com.brick.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JPA가 자동으로 DB 접근하는 코드를 만들어주는 통로
// < Repository가 관리할 엔티티 클래스 , User 엔티티의 PK 타입 (Long) >
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByNickName(String nickName);
    boolean existsByNickNameAndUserIdNot(String nickName, Long userId);
}

