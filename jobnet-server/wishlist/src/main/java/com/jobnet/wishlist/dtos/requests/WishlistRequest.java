package com.jobnet.wishlist.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class WishlistRequest {
    @NotNull(message = "Post ID is required.")
    @NotBlank(message = "Post ID cannot be blank.")
    private String postId;
}
