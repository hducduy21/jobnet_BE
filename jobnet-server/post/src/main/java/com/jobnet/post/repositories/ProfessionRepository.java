package com.jobnet.post.repositories;

import com.jobnet.post.models.Profession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProfessionRepository extends MongoRepository<Profession, String> {

    boolean existsByName(String name);

    boolean existsByIdNotAndName(String id, String name);
}
