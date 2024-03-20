package com.jobnet.application.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobnet.application.models.Application;
import com.jobnet.clients.post.PostResponse;
import com.jobnet.clients.resume.ResumeResponse;
import com.jobnet.clients.user.dtos.responses.JobSeekerResponse;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id"})
public class ApplicationResponse {
    private String id;
    private PostResponse post;
    private JobSeekerResponse jobSeeker;
    private ResumeResponse resume;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createdAt;
    private Application.EApplicationStatus applicationStatus;
}
