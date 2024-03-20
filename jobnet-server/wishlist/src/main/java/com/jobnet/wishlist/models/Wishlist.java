package com.jobnet.wishlist.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("wishlists")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Wishlist {
    @Id
    private String id;
    private String userId;
    private String postId;
    private LocalDate createdAt;
}