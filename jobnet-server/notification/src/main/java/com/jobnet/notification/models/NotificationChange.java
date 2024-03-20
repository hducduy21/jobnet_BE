package com.jobnet.notification.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "notification_changes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NotificationChange {

    @Id
    private String id;

    @DocumentReference
    @Field(name = "notificationObjectId")
    private NotificationObject notificationObject;

    private String actorId;
}
