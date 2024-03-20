package com.jobnet.wishlist.services;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.wishlist.dtos.requests.GetWishlistsFilter;
import com.jobnet.wishlist.dtos.requests.WishlistRequest;
import com.jobnet.wishlist.dtos.responses.WishlistResponse;

import java.util.List;

public interface IWishlistService {

    PaginationResponse<List<WishlistResponse>> getWishlistsByUserId(String userId, GetWishlistsFilter filter);

    WishlistResponse createWishlist(String userId, WishlistRequest wishlistRequest);

    void deleteWishlist(String userId, WishlistRequest wishlistRequest);

    boolean existsWishlist(String userId, String postId);
}
