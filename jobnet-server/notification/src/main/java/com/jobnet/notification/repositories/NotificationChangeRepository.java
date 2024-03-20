package com.jobnet.notification.repositories;

import com.jobnet.notification.models.NotificationChange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationChangeRepository extends MongoRepository<NotificationChange, String> {
}
