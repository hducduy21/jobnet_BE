package com.jobnet.user.repositories;

import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationOTPRepository extends MongoRepository<VerificationOTP, String> {

    Optional<VerificationOTP> findByUserAndToken(User user, String token);
}
