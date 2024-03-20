package com.jobnet.user.repositories;

import com.jobnet.user.models.JobSeeker;
import com.jobnet.user.models.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerRepository extends MongoRepository<JobSeeker, String> {

    Page<JobSeeker> findAllByRole(ERole role, Pageable pageable);

    Optional<JobSeeker> findByIdAndRole(String id, ERole role);

    boolean existsByEmail(String email);

    Optional<JobSeeker> findByEmail(String email);
}
