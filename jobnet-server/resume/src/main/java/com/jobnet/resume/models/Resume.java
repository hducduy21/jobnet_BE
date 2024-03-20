package com.jobnet.resume.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("resumes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Resume {
    @Id
    private String id;
    private String jobSeekerId;
    private String fileId;
    private EAccessPermission accessPermission;
    private ESupportPermission supportPermission;
    private LocalDateTime createdAt;
    private Integer totalViews;
    private List<String> viewerIds;

    public enum EAccessPermission {
        Private,
        Public,
        OnlyRegisteredEmployers,
        OnlyVerifiedRecruiters,
    }

    public enum ESupportPermission {
        Enable,
        Disable,
        UnderReview
    }

}
