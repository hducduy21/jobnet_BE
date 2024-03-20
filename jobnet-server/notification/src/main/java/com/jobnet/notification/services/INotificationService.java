package com.jobnet.notification.services;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.notification.dtos.requests.NotificationRequest;
import com.jobnet.notification.dtos.responses.NotificationObjectResponse;

import com.jobnet.notification.dtos.responses.NotificationResponse;

import java.util.List;

public interface INotificationService {
    PaginationResponse<List<NotificationResponse>> getNotificationsByAuth(String userId, Integer page, Integer pageSize);

    NotificationObjectResponse createNotification(NotificationRequest notificationRequest) throws RuntimeException;
}
