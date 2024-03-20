package com.jobnet.post.dtos.responses;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class ProfessionResponse {
    private String id;
    private String name;
    private String englishName;
    private String categoryId;
    private Integer totalPosts;
}