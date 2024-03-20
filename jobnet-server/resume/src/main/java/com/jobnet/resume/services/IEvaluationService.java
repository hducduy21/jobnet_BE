package com.jobnet.resume.services;

import com.jobnet.resume.dtos.requests.EvaluationRequest;
import com.jobnet.resume.dtos.responses.EvaluationResponse;

import java.util.List;

public interface IEvaluationService {
    List<EvaluationResponse> getEvaluationByResumeId(String resumeId,String userId);

    EvaluationResponse getEvaluationById(String id);

    EvaluationResponse createEvaluation(String resumeId,String userId, EvaluationRequest evaluationRequest);

    EvaluationResponse createComment(String id, String userId,EvaluationRequest evaluationRequest);

    EvaluationResponse deleteComment(String id, String userId ,String commentId);

    void deleteValuationById(String id, String userId);
}
