package com.jobnet.clients.post;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profession {
    private String id;
    private String name;
    private String categoryId;
}
