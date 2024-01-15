package org.system.shared.model.dto;

import lombok.Data;
import org.system.shared.model.entity.NotificationEntity;

import java.util.List;

@Data
public class NotificationDTO {

    private int sizeAll;
    private int sizeToday;

    private List<NotificationEntity> listAll;
    private List<NotificationEntity> listToday;


}
