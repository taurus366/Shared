package org.system.shared.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.system.shared.model.entity.NotificationEntity;

import java.time.Instant;
import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findAllByUserId(int userId);
    List<NotificationEntity> findAllByCreatedAndUserId(Instant created, int userId);
    List<NotificationEntity> findAllByCreatedBetweenAndUserId(Instant createdStart, Instant createdEnd, int userId);

    @Query(value = "SELECT s FROM NotificationEntity s WHERE YEAR(s.created) = :year " +
            "AND MONTH(s.created) = :month " +
            "AND DAY(s.created) = :day " +
            "AND s.userId = :userId")
    List<NotificationEntity> findAllBySameDayAndUserID(int year, int month, int day, int userId);
}
