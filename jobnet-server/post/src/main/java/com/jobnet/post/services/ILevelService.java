package com.jobnet.post.services;

import com.jobnet.post.dtos.requests.LevelRequest;
import com.jobnet.post.dtos.responses.LevelResponse;
import com.jobnet.post.models.Level;
import com.jobnet.post.models.Post;

import java.util.List;

public interface ILevelService {
    List<LevelResponse> getLevels(String search);

    LevelResponse getLevelById(String id);

    LevelResponse createLevel(LevelRequest levelRequest);

    LevelResponse updateLevel(String id, LevelRequest levelRequest);

    void deleteLevelById(String id);

    List<Level> findAllById(Iterable<String> ids);

    Level findByIdOrElseThrow(String levelId);
}
