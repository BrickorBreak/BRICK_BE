package com.brick.repository;

import com.brick.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthUserRepository {

    @PersistenceContext
    private EntityManager em;

    public Optional<User> findById(String userId) {
        String q = "SELECT u FROM User u WHERE u.userId = :userId";

        List<User> result = em.createQuery(q, User.class)
                .setParameter("userId", userId)
                .getResultList();

        return result.stream().findFirst();
    }
}

//이렇게 하면 UserRepository 수정 필요 없다 !