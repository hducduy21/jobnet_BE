package com.jobnet.user.repositories;

import com.jobnet.user.models.Recruiter;
import com.jobnet.user.models.enums.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RecruiterRepository extends MongoRepository<Recruiter, String> {

    Optional<Recruiter> findByIdAndRole(String id, ERole role);

    Optional<Recruiter> findByEmail(String email);

    boolean existsByPhone(String phone);
    boolean existsByIdAndRole(String id, String role);
}
