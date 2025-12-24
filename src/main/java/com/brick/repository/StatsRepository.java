package com.brick.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class StatsRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * 최근 N일 동안 userId의 음식 사진 중
     * (음식 아닌 사진이랑 기본 이미지 제외)
     * 가장 많이 등장한 foodCategory 1개를 반환
     */
    public Optional<TopCategoryRow> findTopFoodCategory(Long userId, LocalDateTime from) {
        List<Object[]> rows = em.createQuery(
                        """
                        select fi.foodCategoryId, count(fi.imageId)
                        from FeedImage fi
                        where fi.userId = :userId
                          and fi.takenTime >= :from
                          and fi.foodCategoryId is not null
                        group by fi.foodCategoryId
                        order by count(fi.imageId) desc
                        """,
                        Object[].class
                )
                .setParameter("userId", userId)
                .setParameter("from", from)
                .setMaxResults(1)
                .getResultList();

        if (rows.isEmpty()) return Optional.empty();

        Object[] r = rows.get(0);
        Long categoryId = (Long) r[0];
        Long cnt = (Long) r[1];

        return Optional.of(new TopCategoryRow(categoryId, cnt));
    }

    public record TopCategoryRow(Long foodCategoryId, Long count) {}
}
