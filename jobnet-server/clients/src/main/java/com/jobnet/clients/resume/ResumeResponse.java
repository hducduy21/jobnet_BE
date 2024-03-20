package com.jobnet.clients.resume;

import com.jobnet.clients.user.dtos.responses.JobSeekerResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResumeResponse {
    private String id;
    private JobSeekerResponse jobSeeker;
    private String fileId;
    private String accessPermission;
    private String supportPermission;
    private LocalDateTime createdAt;
}