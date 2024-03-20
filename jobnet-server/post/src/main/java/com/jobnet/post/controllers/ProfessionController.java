package com.jobnet.post.controllers;

import com.jobnet.post.dtos.requests.ProfessionRequest;
import com.jobnet.post.dtos.responses.ProfessionResponse;
import com.jobnet.post.services.IProfessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/professions")
@RequiredArgsConstructor
@Slf4j
public class ProfessionController {

    private final IProfessionService professionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProfessionResponse> getProfessions(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String categoryId
    ){
        List<ProfessionResponse> professionResponses = professionService.getProfessions(search, categoryId);
        log.info("Get professions successfully");
        return professionResponses;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessionResponse getProfessionById(@PathVariable String id){
        ProfessionResponse professionResponse = professionService.getProfessionById(id);
        log.info("Get profession by id successfully");
        return professionResponse;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfessionResponse createProfession(@RequestBody @Valid ProfessionRequest professionRequest){
        ProfessionResponse professionResponse = professionService.createProfession(professionRequest);
        log.info("Create profession successfully");
        return professionResponse;
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessionResponse updateProfession(
        @PathVariable String id,
        @RequestBody @Valid ProfessionRequest professionRequest
    ){
        ProfessionResponse professionResponse = professionService.updateProfession(id, professionRequest);
        log.info("Update profession successfully");
        return professionResponse;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfessionById(@PathVariable("id") String id){
        professionService.deleteProfessionById(id);
        log.info("Delete profession by ID successfully");
    }
}
