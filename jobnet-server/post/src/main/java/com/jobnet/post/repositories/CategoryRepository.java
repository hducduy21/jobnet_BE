package com.jobnet.post.repositories;

import com.jobnet.post.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByName(String name);

    boolean existsByIdNotAndName(String id, String name);

    List<Category> findByNameContainsIgnoreCase(String search);
}
