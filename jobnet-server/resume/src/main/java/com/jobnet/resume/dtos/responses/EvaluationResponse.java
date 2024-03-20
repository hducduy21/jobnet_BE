package com.jobnet.resume.dtos.responses;

import com.jobnet.resume.models.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EvaluationResponse {
    private String id;
    private Evaluation.Recruiter recruiter;
    private String resumeId;
    private List<Evaluation.Comment> comments;
}
