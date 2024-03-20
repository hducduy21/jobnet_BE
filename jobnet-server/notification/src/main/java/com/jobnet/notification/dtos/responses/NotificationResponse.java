package com.jobnet.notification.dtos.responses;

import com.jobnet.notification.models.Notification;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class NotificationResponse {

    private String id;
    private NotificationObjectResponse notificationObject;
    private String notifierId;
    private Notification.EStatus status;
}
