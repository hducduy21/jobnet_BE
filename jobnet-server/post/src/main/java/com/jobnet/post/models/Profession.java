package com.jobnet.post.models;

import org.springframework.data.annotation.Id;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("professions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Profession {
    @Id
    private String id;

    private String name;
    
    private String englishName;

    private String categoryId;

    private Integer totalPosts;
}
