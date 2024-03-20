package com.jobnet.post.controllers;

import com.jobnet.post.dtos.requests.LevelRequest;
import com.jobnet.post.dtos.responses.LevelResponse;
import com.jobnet.post.services.ILevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/levels")
@RequiredArgsConstructor
@Slf4j
public class LevelController {

    private final ILevelService levelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
     public List<LevelResponse> getLevels(@RequestParam(defaultValue = "") String search) {
        List<LevelResponse> levelResponses = levelService.getLevels(search);
        log.info("Get experience successfully");
        return levelResponses;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelResponse getLevelById(@PathVariable String id) {
        LevelResponse levelResponse = levelService.getLevelById(id);
        log.info("Get experience by ID successfully");
        return levelResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LevelResponse createLevel(@RequestBody @Valid LevelRequest levelRequest) {
        LevelResponse levelResponse = levelService.createLevel(levelRequest);
        log.info("Create experience successfully");
        return levelResponse;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public LevelResponse updateLevel(
        @PathVariable String id,
        @RequestBody @Valid LevelRequest levelRequest
    ) {
        LevelResponse levelResponse = levelService.updateLevel(id, levelRequest);
        log.info("Update experience successfully");
        return levelResponse;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLevelById(@PathVariable String id) {
        levelService.deleteLevelById(id);
        log.info("Delete experience by ID successfully");
    }
}
