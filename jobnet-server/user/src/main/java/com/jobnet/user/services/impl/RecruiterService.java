package com.jobnet.user.services.impl;

import com.jobnet.clients.business.BusinessClient;
import com.jobnet.clients.business.BusinessRegisterRequest;
import com.jobnet.clients.business.BusinessResponse;
import com.jobnet.common.utils.pagination.PaginationResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.common.s3.S3Service;
import com.jobnet.common.utils.EmailUtils;
import com.jobnet.common.utils.pagination.PaginationUtil;
import com.jobnet.user.dtos.requests.*;
import com.jobnet.user.dtos.responses.RawRecruiterResponse;
import com.jobnet.user.dtos.responses.RecruiterResponse;
import com.jobnet.user.mappers.RecruiterMapper;
import com.jobnet.user.models.VerificationOTP;
import com.jobnet.user.models.Recruiter;
import com.jobnet.user.models.enums.ERole;
import com.jobnet.user.repositories.RecruiterRepository;
import com.jobnet.user.services.IVerificationOTPService;
import com.jobnet.user.services.IRecruiterService;
import com.jobnet.common.i18n.MessageUtil;
import lombok.AllArgsConstructor;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RecruiterService implements IRecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final RecruiterMapper recruiterMapper;
    private final PasswordEncoder passwordEncoder;
    private final MongoTemplate mongoTemplate;
    private final BusinessClient businessClient;
    private final S3Service s3Service;
    private final IVerificationOTPService confirmationOTPService;
    private final MessageUtil messageUtil;

    @Override
    public PaginationResponse<List<RecruiterResponse>> getRecruiters(RecruitersGetRequest request) {
        Pageable pageable = PaginationUtil.getPageable(request.getPage(), request.getPageSize(), request.getSortBys());
        Query query = new Query();

        query.addCriteria(Criteria.where("role").regex("Recruiter"));
        if (!StringUtils.isBlank(request.getEmail()))
            query.addCriteria(Criteria.where("email").regex(request.getEmail()));
        if (!StringUtils.isBlank(request.getName()))
            query.addCriteria(Criteria.where("name").regex(request.getName()));
        if (!StringUtils.isBlank(request.getPhone()))
            query.addCriteria(Criteria.where("phone").regex(request.getPhone()));
        // TODO: Fix this
        if (!StringUtils.isBlank(request.getBusiness()))
            query.addCriteria(Criteria.where("business").in());

        Page<Recruiter> recruiterPage = PaginationUtil.getPage(mongoTemplate, query, pageable, Recruiter.class);
        PaginationResponse<List<RecruiterResponse>> response =
            PaginationUtil.getPaginationResponse(recruiterPage, this::getRecruiterResponse);

        log.info(messageUtil.getMessage("success.getAll.recruiter", request, response));
        return response;
    }

    @Override
    public RecruiterResponse getRecruiterById(String id) {
        Recruiter recruiter = this.findByIdAndRoleOrElseThrow(id);

        log.info(messageUtil.getMessage("success.get.recruiter", id, recruiter));
        return this.getRecruiterResponse(recruiter);
    }

    @Override
    public RawRecruiterResponse getRawRecruiterById(String id) {
        Recruiter recruiter = this.findByIdAndRoleOrElseThrow(id);

        log.info(messageUtil.getMessage("success.get.recruiter", id, recruiter));
        return recruiterMapper.convertToRawRecruiterResponse.apply(recruiter);
    }

    @Override
    public Boolean existsById(String id) {
        return recruiterRepository.existsByIdAndRole(id, "Recruiter");
    }

    @Override
    public VerificationOTP createRecruiterWithNewBusiness(RecruiterWithNewBusinessRegisterRequest request) {
        Optional<VerificationOTP> optionalConfirmationOTP =
            this.createConfirmationOTPIfEmailExists(request.getEmail(), request.getPhone());
        if (optionalConfirmationOTP.isPresent())
            return optionalConfirmationOTP.get();

        if (recruiterRepository.existsByPhone(request.getPhone()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.phone"));

        BusinessResponse business = businessClient.createBusiness(
            new BusinessRegisterRequest(
                request.getBusinessName(),
                EmailUtils.extractDomain(request.getEmail())
            )
        );

        Recruiter _recruiter = recruiterMapper
            .convertRecruiterWithNewBusinessRegisterRequestToRecruiter.apply(request);
        this.setDefaultRecruiter(_recruiter, request.getPassword(), business.getId());

        Recruiter recruiter = recruiterRepository.save(_recruiter);

        log.info(messageUtil.getMessage("success.create.recruiter", request, recruiter));
        return confirmationOTPService.createVerificationOTP(recruiter);
    }

    @Override
    public VerificationOTP createRecruiterWithSelectedBusiness(RecruiterWithSelectedBusinessRegisterRequest request) {
        Optional<VerificationOTP> optionalConfirmationOTP =
            this.createConfirmationOTPIfEmailExists(request.getEmail(), request.getPhone());
        if (optionalConfirmationOTP.isPresent())
            return optionalConfirmationOTP.get();

        if (recruiterRepository.existsByPhone(request.getPhone()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.phone"));

        BusinessResponse business = businessClient.getBusinessById(request.getBusinessId());
        if (!EmailUtils.isEmailMatchingDomain(request.getEmail(), business.getEmailDomain()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.notMatchBusinessDomain"));

        Recruiter _recruiter = recruiterMapper
            .convertRecruiterWithSelectedBusinessRegisterRequestToRecruiter.apply(request);
        this.setDefaultRecruiter(_recruiter, request.getPassword(), business.getId());

        Recruiter recruiter = recruiterRepository.save(_recruiter);

        log.info(messageUtil.getMessage("success.create.recruiter.withSelectBusiness", request, recruiter));
        return confirmationOTPService.createVerificationOTP(recruiter);
    }

    private Optional<VerificationOTP> createConfirmationOTPIfEmailExists(String email, String phone) {
        Optional<Recruiter> optionalRecruiter = recruiterRepository.findByEmail(email);
        if (optionalRecruiter.isPresent()) {
            Recruiter recruiter = optionalRecruiter.get();
            if (recruiter.getEnabled())
                throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.email"));
            if (recruiter.getPhone().equals(phone))
                throw new DataIntegrityViolationException(messageUtil.getMessage("error.inUse.phone"));

            return Optional.ofNullable(confirmationOTPService.createVerificationOTP(recruiter));
        }
        return Optional.empty();
    }

    private void setDefaultRecruiter(Recruiter recruiter, String password, String businessId) {
        recruiter.setPassword(passwordEncoder.encode(password));
        recruiter.setRole(ERole.Recruiter);
        recruiter.setBusinessId(businessId);
        recruiter.setLocked(false);
        recruiter.setEnabled(false);
        recruiter.setLocked(false);
    }

    @Override
    public RecruiterResponse updateRecruiterProfile(String id, RecruiterInformation recruiterInformation) {
        Recruiter recruiter = this.findByIdAndRoleOrElseThrow(id);

        recruiter.setName(recruiterInformation.getName());
        recruiter.setPhone(recruiterInformation.getPhone());
        recruiter.setNation(recruiterInformation.getNation());
        recruiterRepository.save(recruiter);

        log.info(messageUtil.getMessage("success.update.recruiter", id, recruiterInformation, recruiter));
        return this.getRecruiterResponse(recruiter);
    }

    @Override
    public RecruiterResponse updateRecruiterBusinessId(String id, RecruiterBusinessRequest request) {
        Recruiter recruiter = this.findByIdAndRoleOrElseThrow(id);

        BusinessResponse business = businessClient.getBusinessById(request.getBusinessId());
        if (!this.hasDomain(recruiter.getEmail(), business.getEmailDomain()))
            throw new DataIntegrityViolationException(messageUtil.getMessage("error.notMatchBusinessDomain"));

        recruiter.setBusinessId(request.getBusinessId());
        recruiterRepository.save(recruiter);

        log.info(messageUtil.getMessage("success.update.recruiter.business", id, request, recruiter));
        return this.getRecruiterResponse(recruiter);
    }

    @Override
    public void deleteRecruiterById(String id, boolean locked) {
        Recruiter recruiter = this.findByIdAndRoleOrElseThrow(id);

        recruiter.setLocked(locked);
        recruiterRepository.save(recruiter);

        log.info(messageUtil.getMessage("success.delete.recruiter", id, locked, recruiter));
    }

    @Override
    public RecruiterResponse uploadRecruiterProfileImage(String id, MultipartFile file) {
        Recruiter recruiter = this.findByIdAndRoleOrElseThrow(id);

        String imageId = StringUtils.isBlank(recruiter.getProfileImageId())
            ? UUID.randomUUID().toString()
            : recruiter.getProfileImageId();;
        String filePath = "recruiters/%s/%s".formatted(id, imageId);
        s3Service.putObject(filePath, file);

        recruiter.setProfileImageId(imageId);
        recruiterRepository.save(recruiter);

        log.info(messageUtil.getMessage("success.updateImage.recruiter", id, file, recruiter));
        return this.getRecruiterResponse(recruiter);
    }

    @Override
    public byte[] getRecruiterProfileImage(String id) {
        Recruiter recruiter = findByIdAndRoleOrElseThrow(id);

        String profileImageId = recruiter.getProfileImageId();
        if (StringUtils.isBlank(profileImageId))
            throw new ResourceNotFoundException(messageUtil.getMessage("error.notFound.profileImage"));

        String filePath = "recruiters/%s/%s".formatted(id, profileImageId);

        log.info(messageUtil.getMessage("success.getImage.recruiter", id, filePath));
        return s3Service.getObject(filePath);
    }

    private Recruiter findByIdAndRoleOrElseThrow(String id) {
        return recruiterRepository.findByIdAndRole(id, ERole.Recruiter)
            .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.notFound.recruiter")));
    }

    private RecruiterResponse getRecruiterResponse(Recruiter recruiter) {
        String businessId = recruiter.getBusinessId();
        BusinessResponse business = !StringUtils.isBlank(businessId)
            ? businessClient.getBusinessById(businessId)
            : null;

        RecruiterResponse recruiterResponse = recruiterMapper.convertToRecruiterResponse.apply(recruiter);
        recruiterResponse.setBusiness(business);
        return recruiterResponse;
    }

    private boolean hasDomain(String email, String domain) {
        String[] parts = email.split("@");
        return parts.length == 2 && parts[1].contains(domain);
    }
}
