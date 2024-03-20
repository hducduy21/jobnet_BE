package com.jobnet.notification.config;

import com.jobnet.common.exceptions.ResourceNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@ConfigurationProperties(prefix = "notification")
@Getter
@Setter
public class NotificationConfig {

    private List<EntityType> entityTypes;

    public EntityType getEntityType(Integer entityTypeId) {
        return this.entityTypes.stream()
            .filter(entityType -> Objects.equals(entityType.getId(), entityTypeId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Entity type ID not found."));
    }

    @Getter
    @Setter
    public static class EntityType {
        private Integer id;
        private String name;
        private String description;
    }
}
