package com.jobnet.wishlist.repositories;

import com.jobnet.wishlist.models.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    Page<Wishlist> findAllByUserId(String userId, Pageable pageable);

    void deleteByUserIdAndPostId(String jobSeekerId, String postId);

    boolean existsByUserIdAndPostId(String jobSeekerId, String postId);

}
