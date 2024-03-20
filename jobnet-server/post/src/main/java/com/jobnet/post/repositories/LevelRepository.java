package com.jobnet.post.repositories;

import com.jobnet.post.models.Level;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends MongoRepository<Level, String> {

    List<Level> findByNameContainsIgnoreCase(String name);

    boolean existsByName(String name);

    boolean existsByIdNotAndName(String id, String name);
}
