package org.system.shared.model.service;

import org.springframework.stereotype.Service;
import org.system.shared.model.entity.NotificationEntity;

import java.time.Instant;
import java.util.List;

public interface NotificationsService {

    List<NotificationEntity> getAllNotificationsByUserId(Long userId);
    NotificationEntity createNewNotification(NotificationEntity en);

    List<NotificationEntity> getAllNotificationsByDateAndUserId(Instant date, Long userId);
    List<NotificationEntity> getAllNotificationsByDateAndUserId(Instant startDate, Instant endDate, Long userId);
    List<NotificationEntity> gettAllSameDayAndUserId(Instant today, Long userId);
}
