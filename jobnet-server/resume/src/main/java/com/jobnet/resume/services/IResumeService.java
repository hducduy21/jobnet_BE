package com.jobnet.resume.services;

import com.jobnet.resume.dtos.requests.FileSaveRequest;
import com.jobnet.resume.dtos.requests.ResumeRequest;
import com.jobnet.resume.dtos.responses.ResumeResponse;

import java.util.List;

public interface IResumeService {

    List<ResumeResponse> getResumesByJobSeekerId(String jobSeekerId);

    ResumeResponse getResumeById(String id);

    byte[] getResumeFile(String id);

    ResumeResponse createResume(String jobSeekerId, FileSaveRequest request);

    ResumeResponse updateResume(String id, ResumeRequest resumeRequest);

    void deleteResumeById(String id);
    boolean existsByResumeId(String resumeId);
}
