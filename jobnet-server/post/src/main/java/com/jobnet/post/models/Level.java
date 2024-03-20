package com.jobnet.post.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("levels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Level {

    @Id
    private String id;
    private String name;
}
