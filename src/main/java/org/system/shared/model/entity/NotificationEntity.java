package org.system.shared.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.system.shared.model.enums.NotificationType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationEntity extends BaseEntity {

    @Column(nullable = false)
    private int userId;
    @Column
    private String title;
    @Column
    private String message;
    @Column
    private Boolean isRead = false;
    @Enumerated(value = EnumType.ORDINAL)
    private NotificationType type;
    @Column
    private String referenceLink;
}
