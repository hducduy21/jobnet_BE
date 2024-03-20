package com.jobnet.clients.post;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Benefit {
    private String id;
    private String name;
    private String description;
}
