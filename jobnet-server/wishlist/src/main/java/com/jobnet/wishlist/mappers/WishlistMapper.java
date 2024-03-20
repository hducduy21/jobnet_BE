package com.jobnet.wishlist.mappers;

import com.jobnet.wishlist.dtos.requests.WishlistRequest;
import com.jobnet.wishlist.dtos.responses.WishlistResponse;
import com.jobnet.wishlist.models.Wishlist;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class WishlistMapper {
    public Function<WishlistRequest, Wishlist> convertToWishlist =
      wishlistRequest ->
        Wishlist.builder()
          .postId(wishlistRequest.getPostId())
          .build();


    public Function<Wishlist, WishlistResponse> convertToWishlistResponse =
      wishlist ->
        WishlistResponse.builder()
          .id(wishlist.getId())
          .createdAt(wishlist.getCreatedAt())
          .build();
}
