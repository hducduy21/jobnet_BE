package com.jobnet.notification.mappers;

import com.jobnet.notification.dtos.requests.NotificationRequest;
import com.jobnet.notification.dtos.responses.NotificationObjectResponse;
import com.jobnet.notification.models.NotificationObject;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class NotificationMapper {
    public Function<NotificationRequest, NotificationObject> convertToNotification =
        notificationRequest ->
            NotificationObject.builder()
                .build();

    public Function<NotificationObject, NotificationObjectResponse> convertToNotificationResponse =
        notificationObject ->
            NotificationObjectResponse.builder()
                .build();
}
