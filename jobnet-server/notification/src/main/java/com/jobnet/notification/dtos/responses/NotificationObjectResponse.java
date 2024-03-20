package com.jobnet.notification.dtos.responses;

import com.jobnet.notification.config.NotificationConfig;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class NotificationObjectResponse {

    private String id;
    private NotificationConfig.EntityType entityType;
    private String entityId;
    private LocalDateTime createdAt;
}
