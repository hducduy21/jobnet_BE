package com.jobnet.user.repositories;

import com.jobnet.user.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByIdNotAndEmail(String id, String email);

    boolean existsByIdNotAndPhone(String id, String phone);
}
