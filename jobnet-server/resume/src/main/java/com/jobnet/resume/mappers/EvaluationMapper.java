package com.jobnet.resume.mappers;

import com.jobnet.resume.dtos.requests.EvaluationRequest;
import com.jobnet.resume.dtos.responses.EvaluationResponse;
import com.jobnet.resume.models.Evaluation;
import org.springframework.stereotype.Service;
import java.util.function.Function;
@Service
public class EvaluationMapper {
    public Function<Evaluation, EvaluationResponse> convertToEvaluationResponse = evaluation ->
            EvaluationResponse.builder()
                    .id(evaluation.getId())
                    .resumeId(evaluation.getResumeId())
                    .recruiter(evaluation.getRecruiter())
                    .comments(evaluation.getComments())
                    .build();
}
