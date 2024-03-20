package com.jobnet.resume.dtos.responses;

import com.jobnet.clients.user.dtos.responses.JobSeekerResponse;
import com.jobnet.resume.models.Resume;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "fileId"})
public class ResumeResponse {
    private String id;
    private JobSeekerResponse jobSeeker;
    private String fileId;
    private Resume.EAccessPermission accessPermission;
    private Resume.ESupportPermission supportPermission;
    private LocalDateTime createdAt;
    private Integer totalViews;
    private List<String> viewerIds;
}
