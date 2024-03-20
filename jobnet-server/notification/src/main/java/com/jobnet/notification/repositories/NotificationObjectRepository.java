package com.jobnet.notification.repositories;

import com.jobnet.notification.models.NotificationObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationObjectRepository extends MongoRepository<NotificationObject, String> {
}