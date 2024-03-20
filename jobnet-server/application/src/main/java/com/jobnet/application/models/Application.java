package com.jobnet.application.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("applications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(exclude = {"createdAt"})
public class Application {
    @Id
    private String id;
    private String postId;
    private String jobSeekerId;
    private String resumeId;
    private LocalDateTime createdAt;
    private EApplicationStatus applicationStatus;

    public enum EApplicationStatus {
        Submitted,
        Reviewed,
        Rejected,
        Hired
    }
}
