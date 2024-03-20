package com.jobnet.notification.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notification_objects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NotificationObject {

    @Id
    private String id;
    private Integer entityTypeId;
    private String entityId;
    private LocalDateTime createdAt;
}
