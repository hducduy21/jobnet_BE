package com.jobnet.post.mappers;

import com.jobnet.post.dtos.requests.LevelRequest;
import com.jobnet.post.dtos.responses.LevelResponse;
import com.jobnet.post.models.Level;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class LevelMapper {

    public Function<LevelRequest, Level> convertToLevel =
        levelRequest ->
            Level.builder()
                .name(levelRequest.getName())
                .build();

    public Function<Level, LevelResponse> convertToLevelResponse =
        level ->
            LevelResponse.builder()
                .id(level.getId())
                .name(level.getName())
                .build();
}
