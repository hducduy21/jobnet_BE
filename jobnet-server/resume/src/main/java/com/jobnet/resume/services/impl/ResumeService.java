package com.jobnet.resume.services.impl;

import com.jobnet.clients.user.UserClient;
import com.jobnet.clients.user.dtos.responses.JobSeekerResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.s3.S3Service;
import com.jobnet.resume.dtos.requests.FileSaveRequest;
import com.jobnet.resume.dtos.requests.ResumeRequest;
import com.jobnet.resume.dtos.responses.ResumeResponse;
import com.jobnet.resume.mappers.ResumeMapper;
import com.jobnet.resume.models.Resume;
import com.jobnet.resume.repositories.ResumeRepository;
import com.jobnet.resume.services.IResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeService implements IResumeService {

    private final ResumeRepository resumeRepository;
    private final UserClient userClient;
    private final ResumeMapper resumeMapper;
    private final S3Service s3Service;
    private final String s3FilePath = "resumes/%s/%s";

    @Override
    public List<ResumeResponse> getResumesByJobSeekerId(String jobSeekerId) {
        List<Resume> resumes = resumeRepository.findAllByJobSeekerId(jobSeekerId);

        log.info("Get resumes by auth - jobSeekerID={}: {}", jobSeekerId, resumes);
        return resumes.stream()
            .map(this::getResumeResponse)
            .toList();
    }

    @Override
    public ResumeResponse getResumeById(String id) {
        Resume resume = this.findByIdOrElseThrow(id);
        resume.setTotalViews(resume.getTotalViews() + 1);
        log.info("Get resume by id={}: {}", id, resume);
        return this.getResumeResponse(resume);
    }

    @Override
    public byte[] getResumeFile(String id) {
        Resume resume = this.findByIdOrElseThrow(id);
        String filePath = s3FilePath.formatted(resume.getJobSeekerId(), resume.getFileId());

        log.info("Get resume file by id={}: {}", id, filePath);
        return s3Service.getObject(filePath);
    }
    @Override
    public ResumeResponse createResume(String jobSeekerId, FileSaveRequest request) {
        JobSeekerResponse jobSeekerResponse = userClient.getJobSeekerById(jobSeekerId);

        if (resumeRepository.countByJobSeekerId(jobSeekerId) >= 5)
            throw new DataIntegrityViolationException("You have exceeded the number of resumes created");

        Resume resume = resumeRepository.save(
            Resume.builder()
                .jobSeekerId(jobSeekerId)
                .accessPermission(Resume.EAccessPermission.Public)
                .supportPermission(Resume.ESupportPermission.Enable)
                .createdAt(LocalDateTime.now())
                .totalViews(0)
                .viewerIds(new ArrayList<>())
                .build()
        );

        String fileId = UUID.randomUUID().toString();
        String filePath = s3FilePath.formatted(jobSeekerId, fileId);
        s3Service.putObject(filePath, request.getFile());
        resume.setFileId(fileId);
        resumeRepository.save(resume);

        ResumeResponse resumeResponse = resumeMapper.convertToResumeResponse.apply(resume);
        resumeResponse.setJobSeeker(jobSeekerResponse);

        log.info("Create resume by auth - jobSeekerID={}, request={}: {}", jobSeekerId, request, resumeResponse);
        return resumeResponse;
    }
    @Override
    public ResumeResponse updateResume(String id, ResumeRequest request) {
        Resume resume = this.findByIdOrElseThrow(id);
        resume.setAccessPermission(Resume.EAccessPermission.valueOf(request.getAccessPermission()));
        resume.setSupportPermission(Resume.ESupportPermission.valueOf(request.getSupportPermission()));
        resumeRepository.save(resume);

        log.info("Update resume by id={}, request={}: {}", id, request, resume);
        return this.getResumeResponse(resume);
    }
    @Override
    public void deleteResumeById(String id) {
        Resume resume = this.findByIdOrElseThrow(id);
        String filePath = s3FilePath.formatted(resume.getJobSeekerId(), resume.getFileId());
        s3Service.deleteObject(filePath);
        resumeRepository.deleteById(id);
        log.info("Delete resume by id={}: {}", id, resume);
    }
    @Override
    public boolean existsByResumeId(String resumeId){
        return resumeRepository.existsById(resumeId);
    }

    private Resume findByIdOrElseThrow(String id) {
        return resumeRepository.findById(id)
            .orElseThrow(() -> new ResourceAccessException("Resume not found."));
    }

    private ResumeResponse getResumeResponse(Resume resume) {
        JobSeekerResponse jobSeekerResponse = userClient.getJobSeekerById(resume.getJobSeekerId());
        ResumeResponse resumeResponse = resumeMapper.convertToResumeResponse.apply(resume);
        resumeResponse.setJobSeeker(jobSeekerResponse);
        return resumeResponse;
    }
}