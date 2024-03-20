package com.jobnet.post.services;

import com.jobnet.post.dtos.requests.ProfessionRequest;
import com.jobnet.post.dtos.responses.ProfessionResponse;
import com.jobnet.post.models.Profession;

import java.util.List;

public interface IProfessionService {

    List<ProfessionResponse> getProfessions(String name, String categoryId);

    ProfessionResponse getProfessionById(String id);

    ProfessionResponse createProfession(ProfessionRequest request);

    ProfessionResponse updateProfession(String id, ProfessionRequest request);

    void updateProfessionTotalPostsById(String id, Integer number);

    void deleteProfessionById(String id);

    Profession findByIdOrElseThrow(String id);
}
