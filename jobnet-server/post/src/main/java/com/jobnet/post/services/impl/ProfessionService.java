package com.jobnet.post.services.impl;

import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.post.dtos.requests.ProfessionRequest;
import com.jobnet.post.dtos.responses.ProfessionResponse;
import com.jobnet.post.mappers.ProfessionMapper;
import com.jobnet.post.models.Profession;
import com.jobnet.post.repositories.CategoryRepository;
import com.jobnet.post.repositories.ProfessionRepository;
import com.jobnet.post.services.IProfessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessionService implements IProfessionService {

    private final ProfessionRepository professionRepository;
    private final CategoryRepository categoryRepository;
    private final ProfessionMapper professionMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<ProfessionResponse> getProfessions(String search, String categoryId) {
        Query query = new Query();

        if (!StringUtils.isBlank(search))
            query.addCriteria(Criteria.where("name").is(search));
        if (!StringUtils.isBlank(categoryId))
            query.addCriteria(Criteria.where("categoryId").is(categoryId));

        List<Profession> professions = mongoTemplate.find(query, Profession.class);
        List<ProfessionResponse> responses = professions.stream()
            .map(this::getProfessionResponse)
            .toList();
        log.info("Get professions: {}", responses);
        return responses;
    }

    @Override
    public ProfessionResponse getProfessionById(String id) {
        Profession profession = this.findByIdOrElseThrow(id);
        ProfessionResponse response = this.getProfessionResponse(profession);
        log.info("Get profession by ID - id={}: {}", id, response);
        return response;
    }

    @Override
    public ProfessionResponse createProfession(ProfessionRequest professionRequest) {
        if (professionRepository.existsByName(professionRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        if (!categoryRepository.existsById(professionRequest.getCategoryId()))
            throw new ResourceNotFoundException("Category not found.");

        Profession _profession = professionMapper.convertToProfession.apply(professionRequest);
        Profession profession = professionRepository.save(_profession);
        ProfessionResponse response = this.getProfessionResponse(profession);
        log.info("Create profession: {}", response);
        return response;
    }

    @Override
    public ProfessionResponse updateProfession(String id, ProfessionRequest professionRequest) {
        Profession profession = findByIdOrElseThrow(id);

        if (professionRepository.existsByIdNotAndName(id, professionRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        if (!categoryRepository.existsById(professionRequest.getCategoryId()))
            throw new ResourceNotFoundException("Category not found.");

        profession.setName(professionRequest.getName());
        profession.setCategoryId(professionRequest.getCategoryId());
        professionRepository.save(profession);
        ProfessionResponse response = this.getProfessionResponse(profession);
        log.info("Update profession: {}", response);
        return response;
    }

    @Override
    public void updateProfessionTotalPostsById(String id, Integer number) {
        Profession profession = this.findByIdOrElseThrow(id);
        profession.setTotalPosts(profession.getTotalPosts() + number);
        ProfessionResponse response = this.getProfessionResponse(profession);
        log.info("Update profession total posts by id={}: {}", id, profession.getTotalPosts());
    }

    @Override
    public void deleteProfessionById(String id)  {
        if (!professionRepository.existsById(id))
            throw new ResourceNotFoundException("Profession not found.");
        professionRepository.deleteById(id);
        log.info("Delete profession by ID - id={}", id);
    }

    @Override
    public Profession findByIdOrElseThrow(String id) {
        return professionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profession not found."));
    }

    private ProfessionResponse getProfessionResponse(Profession profession) {
        return professionMapper.convertToProfessionResponse.apply(profession);
    }

}
