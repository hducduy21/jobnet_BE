package com.jobnet.wishlist.dtos.responses;

import com.jobnet.clients.post.PostResponse;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "post"})
public class WishlistResponse {
    private String id;
    private String userId;
    private PostResponse post;
    private LocalDate createdAt;
}
