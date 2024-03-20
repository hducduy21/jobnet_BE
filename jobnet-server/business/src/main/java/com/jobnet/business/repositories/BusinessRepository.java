package com.jobnet.business.repositories;

import com.jobnet.business.models.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessRepository extends MongoRepository<Business, String> {

    boolean existsByEmailDomain(String emailDomain);

}
