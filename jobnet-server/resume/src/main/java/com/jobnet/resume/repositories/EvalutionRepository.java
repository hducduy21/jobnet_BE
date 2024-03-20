package com.jobnet.resume.repositories;

import com.jobnet.resume.models.Evaluation;
import com.jobnet.resume.models.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvalutionRepository extends MongoRepository<Evaluation, String> {
    List<Evaluation> findAllByResumeId(String resumeId);
    @Query("{ 'resumeId' : ?0, 'recruiter.id' : ?1 }")
    Boolean existsByResumeIdAndRecruiterId(String resumeId, String recruiterId);

    @Query("{ 'resumeId' : ?0, 'recruiter.id' : ?1 }")
    List<Evaluation> findByResumeIdAndRecruiterId(String resumeId, String recruiterId);
}
