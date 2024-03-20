package com.jobnet.resume.models;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Evaluation {
    @Id
    private String id;
    private Recruiter recruiter;
    private String resumeId;
    private List<Comment> comments;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Recruiter {
        private String id;
        private String name;
        private String profileImageId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @Document("comment")
    public static class Comment {
        @Id
        private String id;
        private String content;
        private LocalDate createdAt;
    }
}


