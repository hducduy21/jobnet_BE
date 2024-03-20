package com.jobnet.resume.mappers;

import com.jobnet.resume.dtos.responses.ResumeResponse;
import com.jobnet.resume.models.Resume;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ResumeMapper {

    public Function<Resume, ResumeResponse> convertToResumeResponse =
        resume ->
            ResumeResponse.builder()
                .id(resume.getId())
                .fileId(resume.getFileId())
                .accessPermission(resume.getAccessPermission())
                .supportPermission(resume.getSupportPermission())
                .createdAt(resume.getCreatedAt())
                .totalViews(resume.getTotalViews())
                .viewerIds(resume.getViewerIds())
                .build();
}
