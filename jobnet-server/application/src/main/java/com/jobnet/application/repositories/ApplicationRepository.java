package com.jobnet.application.repositories;

import com.jobnet.application.models.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {

    boolean existsByPostIdAndJobSeekerId(String postId, String jobSeekerId);

    boolean existsByPostIdAndJobSeekerIdAndApplicationStatusIn(
        String postId,
        String jobSeekerId,
        List<Application.EApplicationStatus> applicationStatuses
    );
}
