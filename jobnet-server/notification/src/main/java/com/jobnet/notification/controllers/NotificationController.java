package com.jobnet.notification.controllers;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.notification.dtos.requests.NotificationRequest;
import com.jobnet.notification.dtos.responses.NotificationObjectResponse;
import com.jobnet.notification.dtos.responses.NotificationResponse;
import com.jobnet.notification.services.INotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final INotificationService notificationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)

    public PaginationResponse<List<NotificationResponse>> getNotificationsByAuth(
        @RequestHeader String userId,
        @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer pageSize
    ) {

        PaginationResponse<List<NotificationResponse>> response =
            notificationService.getNotificationsByAuth(userId, page, pageSize);
        log.info("Get notifications by auth successfully");
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationObjectResponse createNotification(@RequestBody @Valid NotificationRequest request) {
        NotificationObjectResponse response = notificationService.createNotification(request);
        log.info("Create notification successfully");
        return response;
    }
}
