package com.jobnet.notification.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class NotificationRequest {

    @NotNull(message = "Entity type ID is required.")
    private Integer entityTypeId;

    @NotNull(message = "Entity ID is required.")
    @NotBlank(message = "Entity ID cannot be blank.")
    private String entityId;

    @NotNull(message = "Actor ID is required.")
    @NotBlank(message = "Actor ID cannot be blank.")
    private String actorId;

    @NotNull(message = "Notifier IDs is required.")
    @NotEmpty(message = "Notifier IDs cannot be empty.")
    private List<String> notifierIds;
}
