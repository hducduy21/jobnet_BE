package com.jobnet.post.services.impl;

import com.jobnet.common.exceptions.DataIntegrityViolationException;
import com.jobnet.common.exceptions.ResourceNotFoundException;
import com.jobnet.post.dtos.requests.LevelRequest;
import com.jobnet.post.dtos.responses.LevelResponse;
import com.jobnet.post.mappers.LevelMapper;
import com.jobnet.post.models.Level;
import com.jobnet.post.models.Post;
import com.jobnet.post.repositories.LevelRepository;
import com.jobnet.post.services.ILevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LevelService implements ILevelService {

    private final LevelRepository levelRepository;
    private final LevelMapper levelMapper;

    @Override
    public List<LevelResponse> getLevels(String search) {
        List<Level> levels = StringUtils.isBlank(search)
            ? levelRepository.findAll()
            : levelRepository.findByNameContainsIgnoreCase(search);

        List<LevelResponse> responses = levels.stream()
            .map(this::getExperienceResponse)
            .toList();
        log.info("Get levels: {}", responses);
        return responses;
    }

    @Override
    public LevelResponse getLevelById(String id) {
        Level level = this.findByIdOrElseThrow(id);
        LevelResponse response = this.getExperienceResponse(level);
        log.info("Get level by ID - id={}: {}", id, response);
        return response;
    }

    @Override
    public LevelResponse createLevel(LevelRequest levelRequest) {
        if (levelRepository.existsByName(levelRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        Level _level = levelMapper.convertToLevel.apply(levelRequest);
        Level level = levelRepository.save(_level);
        LevelResponse response = getExperienceResponse(level);
        log.info("Create level: {}", response);
        return response;
    }

    @Override
    public LevelResponse updateLevel(String id, LevelRequest levelRequest) {
        Level level = this.findByIdOrElseThrow(id);

        if (levelRepository.existsByIdNotAndName(id, levelRequest.getName()))
            throw new DataIntegrityViolationException("Name is already in use.");

        level.setName(levelRequest.getName());
        levelRepository.save(level);
        LevelResponse response = this.getExperienceResponse(level);
        log.info("Update level: {}", response);
        return response;
    }

    @Override
    public void deleteLevelById(String id) {
        if (!levelRepository.existsById(id))
            throw new ResourceNotFoundException("Level not found.");
        levelRepository.deleteById(id);
        log.info("Delete level by ID - id={}", id);
    }

    @Override
    public List<Level> findAllById(Iterable<String> ids) {
        return levelRepository.findAllById(ids);
    }

    @Override
    public Level findByIdOrElseThrow(String id) {
        return levelRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Level not found."));
    }

    private LevelResponse getExperienceResponse(Level level) {
        return levelMapper.convertToLevelResponse.apply(level);
    }
}


