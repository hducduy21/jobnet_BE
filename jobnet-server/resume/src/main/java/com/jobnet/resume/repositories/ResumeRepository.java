package com.jobnet.resume.repositories;

import com.jobnet.resume.models.Resume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends MongoRepository<Resume, String> {

    List<Resume> findAllByJobSeekerId(String jobSeekerId);

    long countByJobSeekerId(String jobSeekerId);

}
