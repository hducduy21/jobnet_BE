package com.jobnet.post.mappers;

import com.jobnet.post.dtos.requests.PostCreateRequest;
import com.jobnet.post.dtos.responses.PostResponse;
import com.jobnet.post.models.Post;
import com.jobnet.post.repositories.PostRepository;
import com.jobnet.post.services.IProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.regex.Pattern;

@Service
public class PostMapper {
    private PostRepository postRepository;
    private IProfessionService professionService;
    @Autowired
    public PostMapper(PostRepository postRepository, IProfessionService professionService){
        this.postRepository = postRepository;
        this.professionService = professionService;
    }
    private final Pattern isNumericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
    public Function<PostCreateRequest, Post> convertToPost =
        request -> {
         Post post = Post.builder()
                         .title(request.getTitle())
                         .minSalary(null)
                         .minSalaryString(request.getMinSalaryString())
                         .maxSalary(null)
                         .maxSalaryString(request.getMaxSalaryString())
                         .currency(request.getCurrency())
                         .locations(request.getLocations())
                         .workingFormat(request.getWorkingFormat())
                         .description(request.getDescription())
                         .yearsOfExperience(request.getYearsOfExperience())
                         .otherRequirements(request.getOtherRequirements())
                         .requisitionNumber(request.getRequisitionNumber())
                         .applicationDeadline(
                             LocalDate.parse(request.getApplicationDeadline(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                         )
                         .build();
         if (isNumericPattern.matcher(request.getMinSalaryString()).matches()){
             post.setMinSalary(new BigInteger(request.getMinSalaryString()));
             post.setMaxSalary(new BigInteger(request.getMaxSalaryString()));
         }
         return post;
        };
        
        

    public Function<Post, PostResponse> convertToPostResponse =
        post -> {
            if(post.getApplicationDeadline().isBefore(LocalDate.now()) && !post.getActiveStatus().equals(Post.EActiveStatus.Stopped)){
                post.setActiveStatus(Post.EActiveStatus.Stopped);
                postRepository.save(post);
                professionService.updateProfessionTotalPostsById(post.getProfession().getId(), -1);
            }
            return PostResponse.builder()
                       .id(post.getId())
                       .title(post.getTitle())
                       .profession(post.getProfession())
                       .minSalary(post.getMinSalary())
                       .minSalaryString(post.getMinSalaryString())
                       .maxSalary(post.getMaxSalary())
                       .maxSalaryString(post.getMaxSalaryString())
                       .currency(post.getCurrency())
                       .level(post.getLevel())
                       .locations(post.getLocations())
                       .workingFormat(post.getWorkingFormat())
                       .benefits(post.getBenefits())
                       .description(post.getDescription())
                       .yearsOfExperience(post.getYearsOfExperience())
                       .otherRequirements(post.getOtherRequirements())
                       .requisitionNumber(post.getRequisitionNumber())
                       .applicationDeadline(post.getApplicationDeadline())
                       .jdId(post.getJdId())
                       .recruiterId(post.getRecruiterId())
                       .business(post.getBusiness())
                       .activeStatus(post.getActiveStatus())
                       .totalApplications(post.getTotalApplications())
                       .totalViews(post.getTotalViews())
                       .createdAt(post.getCreatedAt())
                       .build();
        };
        
}
