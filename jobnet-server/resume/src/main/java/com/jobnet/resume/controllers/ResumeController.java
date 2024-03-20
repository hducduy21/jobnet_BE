package com.jobnet.resume.controllers;

import com.jobnet.resume.dtos.requests.EvaluationRequest;
import com.jobnet.resume.dtos.requests.FileSaveRequest;
import com.jobnet.resume.dtos.requests.ResumeRequest;
import com.jobnet.resume.dtos.responses.EvaluationResponse;
import com.jobnet.resume.dtos.responses.ResumeResponse;
import com.jobnet.resume.services.IEvaluationService;
import com.jobnet.resume.services.IResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/resumes")
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final IResumeService resumeService;
    private final IEvaluationService evaluationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResumeResponse> getResumesByAuth(@RequestHeader String userId) {
        List<ResumeResponse> resumeResponses = resumeService.getResumesByJobSeekerId(userId);

        log.info("Get resumes by userId successfully.");
        return resumeResponses;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResumeResponse getResumeById(@PathVariable String id) {
        ResumeResponse resumeResponse = resumeService.getResumeById(id);

        log.info("Get resume by id successfully.");
        return resumeResponse;
    }

    @GetMapping(
        value = "{id}/file",
        produces = MediaType.APPLICATION_PDF_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getResumeFile(@PathVariable String id) {
        byte[] resumeFile = resumeService.getResumeFile(id);

        log.info("Get resume file by id successfully.");
        return resumeFile;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResumeResponse createResume(
        @RequestHeader String userId,
        @ModelAttribute @Valid FileSaveRequest request
    ) {
        ResumeResponse response = resumeService.createResume(userId, request);

        log.info("Creat resume by userId successfully.");
        return response;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResumeResponse updateResume(
        @PathVariable String id,
        @RequestBody @Valid ResumeRequest resumeRequest
    ) {
        ResumeResponse resumeResponse = resumeService.updateResume(id, resumeRequest);

        log.info("Update resume by id successfully.");
        return resumeResponse;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResume(@PathVariable String id) {
        resumeService.deleteResumeById(id);
        log.info("Delete resume by id successfully.");
    }

    @GetMapping("{id}/evaluations")
    @ResponseStatus(HttpStatus.OK)
    public List<EvaluationResponse> getEvaluationByAuth(
            @PathVariable String id,
            @RequestHeader String userId
            ) {
        List<EvaluationResponse> evaluationResponses = evaluationService.getEvaluationByResumeId(id,userId);

        log.info("Get evaluation by resumeId successfully.");
        return evaluationResponses;
    }

    @GetMapping("evaluations/{evaluationId}")
    @ResponseStatus(HttpStatus.OK)
    public EvaluationResponse getEvaluationById(@PathVariable String evaluationId) {
        EvaluationResponse evaluationResponse = evaluationService.getEvaluationById(evaluationId);

        log.info("Get evaluation by id = {} successfully.", evaluationId);
        return evaluationResponse;
    }

    @PostMapping("{id}/evaluations")
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluationResponse createEvaluation(
            @RequestHeader String userId,
            @PathVariable String id,
            @RequestBody @Valid EvaluationRequest evaluationRequest
    ) {
        EvaluationResponse evaluationResponse = evaluationService.createEvaluation(id, userId, evaluationRequest);

        log.info("Creat evaluation by userId successfully.");
        return evaluationResponse;
    }

    @PostMapping("evaluations/{evaluationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluationResponse createComment(
            @RequestHeader String userId,
            @PathVariable String evaluationId,
            @RequestBody @Valid EvaluationRequest evaluationRequest
    ) {
        EvaluationResponse evaluationResponse = evaluationService.createComment(evaluationId,userId, evaluationRequest);

        log.info("Creat comment by userId successfully.");
        return evaluationResponse;
    }
    @DeleteMapping("evaluations/{evaluationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvaluation(@PathVariable String evaluationId,@RequestHeader String userId) {
        evaluationService.deleteValuationById(evaluationId,userId);
        log.info("Delete evaluation by id successfully.");
    }
    @DeleteMapping("{id}/evaluations/comments/{commandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommand(@PathVariable String id,@PathVariable String commandId, @RequestHeader String userId) {
        evaluationService.deleteComment(id,userId,commandId);
        log.info("Delete comment by id successfully.");
    }

}
