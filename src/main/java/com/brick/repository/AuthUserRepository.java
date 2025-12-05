package com.brick.repository;

import com.brick.entity.AuthUser;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findById(String id);

    Optional<AuthUser> findByPhone(String phone);
}

//이렇게 하면 UserRepository 수정 필요 없다 !