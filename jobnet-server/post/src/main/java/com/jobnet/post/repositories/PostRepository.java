package com.jobnet.post.repositories;

import com.jobnet.post.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    boolean existsByTitle(String title);

    boolean existsByIdNotAndTitle(String id, String title);

    List<Post> findPostsByRecruiterId(String recruiterId);

    List<Post> findPostsByActiveStatus(Post.EActiveStatus activeStatus);
}
