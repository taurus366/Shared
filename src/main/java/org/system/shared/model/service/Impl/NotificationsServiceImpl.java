package org.system.shared.model.service.Impl;

import org.springframework.stereotype.Service;
import org.system.shared.model.entity.NotificationEntity;
import org.system.shared.model.repository.NotificationsRepository;
import org.system.shared.model.service.NotificationsService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private final NotificationsRepository notificationsRepository;

    public NotificationsServiceImpl(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public List<NotificationEntity> getAllNotificationsByUserId(Long userId) {
        return notificationsRepository.findAllByUserId(userId.intValue());
    }

    @Override
    public NotificationEntity createNewNotification(NotificationEntity en) {
        return notificationsRepository.save(en);
    }

    @Override
    public List<NotificationEntity> getAllNotificationsByDateAndUserId(Instant date, Long userId) {
        return notificationsRepository.findAllByCreatedAndUserId(date, userId.intValue());
    }

    @Override
    public List<NotificationEntity> getAllNotificationsByDateAndUserId(Instant startDate, Instant endDate, Long userId) {
        return notificationsRepository.findAllByCreatedBetweenAndUserId(startDate, endDate, userId.intValue());
    }

    @Override
    public List<NotificationEntity> gettAllSameDayAndUserId(Instant today, Long userId) {

        final ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(today, ZoneId.systemDefault());

       return notificationsRepository.findAllBySameDayAndUserID(zonedDateTime.getYear(), zonedDateTime.getMonth().getValue(), zonedDateTime.getDayOfMonth(), userId.intValue());
    }
}
