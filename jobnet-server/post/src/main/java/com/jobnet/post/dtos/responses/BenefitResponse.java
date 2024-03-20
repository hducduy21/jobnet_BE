package com.jobnet.post.dtos.responses;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "name"})
@Builder
public class BenefitResponse {
    private String id;
    private String name;
    private String description;
}
