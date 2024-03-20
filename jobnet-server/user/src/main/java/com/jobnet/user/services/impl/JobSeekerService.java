package com.jobnet.user.services.impl;

import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.common.s3.S3Service;
import com.jobnet.common.utils.pagination.PaginationUtil;
import com.jobnet.user.dtos.requests.*;
import com.jobnet.user.dtos.responses.JobSeekerResponse;
import com.jobnet.user.mappers.JobSeekerMapper;
import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.JobSeeker;
import com.jobnet.user.models.enums.*;
import com.jobnet.user.repositories.JobSeekerRepository;
import com.jobnet.user.services.IVerificationOTPService;
import com.jobnet.user.services.IJobSeekerService;
import com.jobnet.common.i18n.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobSeekerService implements IJobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JobSeekerMapper jobSeekerMapper;
    private final S3Service s3Service;
    private final IVerificationOTPService confirmationOTPService;
    private final MessageUtil messageUtil;

    @Override
    public PaginationResponse<List<JobSeekerResponse>> getJobSeekers(JobSeekersGetRequest request){
        Pageable pageable = PaginationUtil.getPageable(request.getPage(), request.getPageSize(), request.getSortBys());
        Query query = new Query();

        query.addCriteria(Criteria.where("role").regex("JobSeeker"));
        if (!StringUtils.isBlank(request.getEmail()))
            query.addCriteria(Criteria.where("email").regex(request.getEmail()));
        if (!StringUtils.isBlank(request.getName()))
            query.addCriteria(Criteria.where("name").regex(request.getName()));
        if (!StringUtils.isBlank(request.getPhone()))
            query.addCriteria(Criteria.where("phone").regex(request.getPhone()));
        if (!StringUtils.isBlank(request.getVerificationStatus()))
            query.addCriteria(Criteria.where("verificationStatus").regex(request.getVerificationStatus()));
        if (!StringUtils.isBlank(request.getAccountType()))
            query.addCriteria(Criteria.where("accountType").regex(request.getAccountType()));

        Page<JobSeeker> jobSeekerPage = PaginationUtil.getPage(mongoTemplate, query, pageable, JobSeeker.class);
        PaginationResponse<List<JobSeekerResponse>> response =
            PaginationUtil.getPaginationResponse(jobSeekerPage, this::getJobSeekerResponse);

        log.info(messageUtil.getMessage("success.getAll.jobSeeker", request));
        return response;
    }

    @Override
    public JobSeekerResponse getJobSeekerById(String id) {
        JobSeeker jobSeeker = findByIdOrElseThrow(id);

        log.info(messageUtil.getMessage("success.get.jobSeeker", id, jobSeeker));
        return getJobSeekerResponse(jobSeeker);
    }

    @Override
    public VerificationOTP createJobSeeker(JobSeekerRegisterRequest registerRequest) {
        Optional<JobSeeker> optionalJobSeeker = jobSeekerRepository.findByEmail(registerRequest.getEmail());
        if (optionalJobSeeker.isPresent()) {
            JobSeeker jobSeeker = optionalJobSeeker.get();
            if (jobSeeker.getEnabled())
                throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.email"));
            return confirmationOTPService.createVerificationOTP(jobSeeker);
        }

        JobSeeker _jobSeeker = jobSeekerMapper.convertRegisterRequestToJobSeeker.apply(registerRequest);
        _jobSeeker.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        _jobSeeker.setRole(ERole.JobSeeker);
        _jobSeeker.setLocked(false);
        _jobSeeker.setEnabled(false);
        _jobSeeker.setVerificationStatus(EVerificationStatus.Pending);
        _jobSeeker.setJobSearchStatus(EJobSearchStatus.ActivelySearch);
        _jobSeeker.setAccountType(EAccountType.Free);
        _jobSeeker.setSubscribesToJobOpportunities(true);
        _jobSeeker.setBusinessFollowed(new ArrayList<>());
        _jobSeeker.setTotalBusinessFollowed(0);

        JobSeeker jobSeeker = jobSeekerRepository.save(_jobSeeker);

        log.info(messageUtil.getMessage("success.create.jobSeeker", registerRequest, jobSeeker));
        return confirmationOTPService.createVerificationOTP(jobSeeker);
    }

    @Override
    public JobSeekerResponse updateJobSeekerPersonalInfo(String id, JobSeekerPersonalInfo personalInfo) {
        JobSeeker jobSeeker = this.findByIdOrElseThrow(id);
        jobSeeker.setName(personalInfo.getName());
        jobSeeker.setEmail(personalInfo.getEmail());
        jobSeeker.setPhone(personalInfo.getPhone());
        jobSeeker.setNation(personalInfo.getNation());

        jobSeekerRepository.save(jobSeeker);

        log.info(messageUtil.getMessage("success.update.jobSeeker", id, personalInfo, jobSeeker));
        return getJobSeekerResponse(jobSeeker);
    }

    @Override
    public JobSeekerResponse updateJobSeekerProfessionInfo(String id, JobSeekerProfessionInfo professionInfo) {
        JobSeeker jobSeeker = this.findByIdOrElseThrow(id);
        jobSeeker.setSalary(professionInfo.getSalary());
        jobSeeker.setWorkingFormat(professionInfo.getWorkingFormat());
        jobSeeker.setProfession(professionInfo.getProfession());
        jobSeeker.setLocation(professionInfo.getLocation());

        jobSeekerRepository.save(jobSeeker);

        log.info(messageUtil.getMessage("success.update.jobSeeker", id, professionInfo, jobSeeker));
        return getJobSeekerResponse(jobSeeker);
    }

    @Override
    public JobSeekerResponse updateJobSeekerBusinessFollowed(String id, JobSeekerBusinessFollowedInfo request) {
        JobSeeker jobSeeker = this.findByIdOrElseThrow(id);
        if(request.getStatus().name().equals(EFollowAction.FOLLOW.name())){
            jobSeeker.getBusinessFollowed().add(request.getBusinessId());
            jobSeeker.setTotalBusinessFollowed(jobSeeker.getTotalBusinessFollowed() + 1);
        }
        if(request.getStatus().name().equals(EFollowAction.UNFOLLOW.name())){
            jobSeeker.getBusinessFollowed().remove(request.getBusinessId());
            jobSeeker.setTotalBusinessFollowed(jobSeeker.getTotalBusinessFollowed() - 1);
        }
        jobSeeker.setBusinessFollowed(jobSeeker.getBusinessFollowed());
        jobSeekerRepository.save(jobSeeker);

        log.info(messageUtil.getMessage("success.update.jobSeeker", id, request, jobSeeker));
        return getJobSeekerResponse(jobSeeker);
    }
    
    @Override
    public void deleteJobSeekerById(String id, boolean locked) {
        JobSeeker jobSeeker = this.findByIdOrElseThrow(id);
        jobSeeker.setLocked(locked);

        jobSeekerRepository.save(jobSeeker);
        log.info(messageUtil.getMessage("success.delete.jobSeeker", id, locked, jobSeeker));
    }

    @Override
    public JobSeekerResponse uploadJobSeekerProfileImage(String id, MultipartFile file) {
        JobSeeker jobSeeker = this.findByIdOrElseThrow(id);

        String imageId = StringUtils.isBlank(jobSeeker.getProfileImageId())
            ? UUID.randomUUID().toString()
            : jobSeeker.getProfileImageId();
        String filePath = "jobSeekers/%s/%s".formatted(id, imageId);
        s3Service.putObject(filePath, file);

        jobSeeker.setProfileImageId(imageId);
        jobSeekerRepository.save(jobSeeker);

        log.info(messageUtil.getMessage("success.updateImage.jobSeeker", id, file, jobSeeker));
        return getJobSeekerResponse(jobSeeker);
    }

    @Override
    public byte[] getJobSeekerProfileImage(String id) {
        JobSeeker jobSeeker = this.findByIdOrElseThrow(id);

        String profileImageId = jobSeeker.getProfileImageId();
        if (StringUtils.isBlank(profileImageId))
            throw new ResourceNotFoundException(messageUtil.getMessage("error.notFound.profileImage"));

        String filePath = "jobSeekers/%s/%s".formatted(id, profileImageId);

        log.info(messageUtil.getMessage("success.getImage.jobSeeker", id, filePath));
        return s3Service.getObject(filePath);
    }

    private JobSeeker findByIdOrElseThrow(String id) {
        return jobSeekerRepository.findByIdAndRole(id, ERole.JobSeeker)
            .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.notFound.jobSeeker")));
    }

    private JobSeekerResponse getJobSeekerResponse(JobSeeker jobSeeker) {
        JobSeekerResponse jobSeekerResponse = jobSeekerMapper.convertToJobSeekerResponse.apply(jobSeeker);
        jobSeekerResponse.setBusinessFollowed(jobSeeker.getBusinessFollowed());
        return jobSeekerResponse;
    }
}
