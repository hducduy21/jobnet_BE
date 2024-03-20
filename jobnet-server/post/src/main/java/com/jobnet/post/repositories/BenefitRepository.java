package com.jobnet.post.repositories;

import com.jobnet.post.models.Benefit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends MongoRepository<Benefit,String> {

    List<Benefit> findByNameContainsIgnoreCase(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(String id, String name);
}
