package com.jobnet.notification.services.Impl;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.notification.config.NotificationConfig;
import com.jobnet.notification.dtos.requests.NotificationRequest;
import com.jobnet.notification.dtos.responses.NotificationObjectResponse;
import com.jobnet.notification.dtos.responses.NotificationResponse;
import com.jobnet.notification.models.Notification;
import com.jobnet.notification.models.NotificationChange;
import com.jobnet.notification.models.NotificationObject;
import com.jobnet.notification.repositories.NotificationChangeRepository;
import com.jobnet.notification.repositories.NotificationObjectRepository;
import com.jobnet.notification.repositories.NotificationRepository;
import com.jobnet.notification.services.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService implements INotificationService {

    private final NotificationConfig notificationConfig;
    private final NotificationObjectRepository notificationObjectRepository;
    private final NotificationChangeRepository notificationChangeRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public PaginationResponse<List<NotificationResponse>> getNotificationsByAuth(
        String userId,
        Integer page,
        Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(
            page - 1,
            pageSize,
            Sort.by("createdAt").descending()
        );
        Page<Notification> notificationPage = notificationRepository.findAllByNotifierId(
            userId,
            pageable
        );

        long totalElements = notificationPage.getTotalElements();
        int totalPages = notificationPage.getTotalPages();
        int currentPage = notificationPage.getNumber() + 1;
        boolean hasNextPage = notificationPage.hasNext();
        List<NotificationResponse> notificationResponses = notificationPage.getContent().stream()
            .map(notification -> NotificationResponse.builder()
                .id(notification.getId())
                .notificationObject(this.getNotificationObjectResponse(notification.getNotificationObject()))
                .notifierId(notification.getNotifierId())
                .status(notification.getStatus())
                .build()
            ).collect(Collectors.toList());

        PaginationResponse<List<NotificationResponse>> response =
            PaginationResponse.<List<NotificationResponse>>builder()
                .totalElements(totalElements)
                .totalPages(totalPages)
                .currentPage(currentPage)
                .hasNextPage(hasNextPage)
                .data(notificationResponses)
                .build();
        log.info("Get notification by auth - userId={}: {}", userId, response);
        return response;
    }

    @Override
    public NotificationObjectResponse createNotification(NotificationRequest request) {
        NotificationObject notificationObject = notificationObjectRepository.save(
            NotificationObject.builder()
                .entityTypeId(request.getEntityTypeId())
                .entityId(request.getEntityId())
                .createdAt(LocalDateTime.now())
                .build()
        );
        notificationChangeRepository.save(
            NotificationChange.builder()
                .notificationObject(notificationObject)
                .actorId(request.getActorId())
                .build()
        );
        notificationRepository.saveAll(
            request.getNotifierIds().stream()
                .map(notifierId -> Notification.builder()
                    .notificationObject(notificationObject)
                    .notifierId(notifierId)
                    .status(Notification.EStatus.Unread)
                    .build())
                .collect(Collectors.toList())
        );
        NotificationObjectResponse response = this.getNotificationObjectResponse(notificationObject);
        log.info("Create notification - request={}: {}", request, response);
        return response;
    }

    private NotificationObjectResponse getNotificationObjectResponse(NotificationObject notificationObject) {
        return NotificationObjectResponse.builder()
            .id(notificationObject.getId())
            .entityType(notificationConfig.getEntityType(notificationObject.getEntityTypeId()))
            .entityId(notificationObject.getEntityId())
            .createdAt(notificationObject.getCreatedAt())
            .build();
    }
}
