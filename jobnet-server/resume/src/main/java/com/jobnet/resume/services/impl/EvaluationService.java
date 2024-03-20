package com.jobnet.resume.services.impl;

import com.jobnet.clients.user.dtos.responses.RawRecruiterResponse;
import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.resume.dtos.requests.EvaluationRequest;
import com.jobnet.resume.dtos.responses.EvaluationResponse;
import com.jobnet.resume.mappers.EvaluationMapper;
import com.jobnet.resume.models.Evaluation;
import com.jobnet.resume.repositories.EvalutionRepository;
import com.jobnet.resume.services.IEvaluationService;
import com.jobnet.resume.services.IResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import com.jobnet.clients.user.UserClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluationService implements IEvaluationService {
    private final EvalutionRepository evalutionRepository;
    private final IResumeService resumeService;
    private final UserClient userClient;
    private final EvaluationMapper evaluationMapper;

    @Override
    public List<EvaluationResponse> getEvaluationByResumeId(String resumeId, String userId){
        List<Evaluation> evaluations;
        if(userClient.existsRecruiterById(userId)){
            evaluations = evalutionRepository.findByResumeIdAndRecruiterId(resumeId,userId);
        }else{
            evaluations = evalutionRepository.findAllByResumeId(resumeId);
        }
        return evaluations.stream()
                .map(this::getEvaluationResponse)
                .toList();
    }

    @Override
    public EvaluationResponse getEvaluationById(String id){
        Evaluation evaluation = this.findByIdOrElseThrow(id);

        log.info("Get resume by id={}: {}", id, evaluation);
        return this.getEvaluationResponse(evaluation);
    }

    public EvaluationResponse createEvaluation(String resumeId,String userId, EvaluationRequest evaluationRequest){
        if (!resumeService.existsByResumeId(resumeId))
            throw new DataIntegrityViolationException("Resume does not exist.");

        if(evalutionRepository.existsByResumeIdAndRecruiterId(resumeId, userId) != null){
            throw new DataIntegrityViolationException("Evaluation exists, please create comment.");
        }

        Evaluation.Recruiter recruiter = getRecruiter(userId);
        Evaluation.Comment comment = Evaluation.Comment.builder()
                .id(UUID.randomUUID().toString())
                .content(evaluationRequest.getComment())
                .createdAt(LocalDate.now())
                .build();
        List<Evaluation.Comment> comments = new ArrayList();
        comments.add(comment);

        Evaluation _evaluation = Evaluation.builder()
                .recruiter(recruiter)
                .resumeId(resumeId)
                .comments(comments).build();

        evalutionRepository.save(_evaluation);

        EvaluationResponse evaluationResponse = evaluationMapper.convertToEvaluationResponse.apply(_evaluation);

        log.info("Create evaluation by auth - recruiterID={}, request={}: {}", userId, evaluationRequest, evaluationResponse);
        return evaluationResponse;
    }

    @Override
    public EvaluationResponse createComment(String id, String userId,EvaluationRequest evaluationRequest){
        Evaluation evaluation = this.findByIdOrElseThrow(id);

        if (!Objects.equals(evaluation.getRecruiter().getId() , userId))
            throw new ResourceAccessException("User is not authorized.");

        Evaluation.Comment comment = Evaluation.Comment.builder()
                .id(UUID.randomUUID().toString())
                .content(evaluationRequest.getComment())
                .createdAt(LocalDate.now()).build();
        List<Evaluation.Comment> comments = evaluation.getComments();
        comments.add(comment);
        evaluation.setComments(comments);

        evalutionRepository.save(evaluation);

        log.info("Update evaluation by id={}, evaluation: {}", id, evaluation);
        return this.getEvaluationResponse(evaluation);
    }

    @Override
    public EvaluationResponse deleteComment(String id,String userId, String commentId){
        Evaluation evaluation = this.findByIdOrElseThrow(id);
        if(!Objects.equals(userId, evaluation.getRecruiter().getId()))
            throw new DataIntegrityViolationException("User is not authorized.");
        Evaluation.Comment comment = evaluation.getComments().stream().filter(q->q.getId().equals(commentId) ).findFirst().get();
        List<Evaluation.Comment> comments = evaluation.getComments();
        comments.remove(comment);
        evaluation.setComments(comments);

        evalutionRepository.save(evaluation);

        log.info("Update evaluation by id={}, evaluation: {}", id, evaluation);
        return this.getEvaluationResponse(evaluation);
    }

    @Override
    public void deleteValuationById(String id, String userId){
        Evaluation evaluation = this.findByIdOrElseThrow(id);
        if(!Objects.equals(userId, evaluation.getRecruiter().getId()))
            throw new DataIntegrityViolationException("User is not authorized.");
        evalutionRepository.deleteById(id);

        log.info("Delete evaluation by id={}", id);
    }

    private EvaluationResponse getEvaluationResponse(Evaluation evaluation) {
        EvaluationResponse evaluationResponse = evaluationMapper.convertToEvaluationResponse.apply(evaluation);
        return evaluationResponse;
    }

    private Evaluation findByIdOrElseThrow(String id) {
        return evalutionRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Evaluation not found."));
    }
    private Evaluation.Recruiter getRecruiter(String id) {
        RawRecruiterResponse recruiter =  userClient.getRawRecruiterById(id);
        return Evaluation.Recruiter.builder()
                .id(id)
                .name(recruiter.getName())
                .profileImageId(recruiter.getProfileImageId()).build();
    }
}
