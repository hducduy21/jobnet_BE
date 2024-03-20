package com.jobnet.post.mappers;

import com.jobnet.post.dtos.requests.ProfessionRequest;
import com.jobnet.post.dtos.responses.ProfessionResponse;
import com.jobnet.post.models.Profession;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProfessionMapper {

    public Function<ProfessionRequest, Profession> convertToProfession =
        professionRequest ->
            Profession.builder()
                .name(professionRequest.getName())
                .englishName(professionRequest.getEnglishName())
                .categoryId(professionRequest.getCategoryId())
                .totalPosts(0)
                .build();

    public Function<Profession, ProfessionResponse> convertToProfessionResponse =
        profession ->
            ProfessionResponse.builder()
                .id(profession.getId())
                .name(profession.getName())
                .englishName(profession.getEnglishName())
                .categoryId(profession.getCategoryId())
                .totalPosts(profession.getTotalPosts())
                .build();
}
