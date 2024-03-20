package com.jobnet.post.dtos.responses;

import com.jobnet.common.dtos.LocationRequest;
import com.jobnet.post.models.Post;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(of = {"id", "title"})
@Builder
public class PostResponse {
    private String id;
    private String title;
    private Post.Profession profession;
    private BigInteger minSalary;
    private String minSalaryString;
    private BigInteger maxSalary;
    private String maxSalaryString;
    private String currency;
    private Post.Level level;
    private List<LocationRequest> locations;
    private String workingFormat;
    private List<Post.Benefit> benefits;
    private String description;
    private String yearsOfExperience;
    private String otherRequirements;
    private Integer requisitionNumber;
    private LocalDate applicationDeadline;
    private String jdId;
    private String recruiterId;
    private Post.Business business;
    private Post.EActiveStatus activeStatus;
    private Integer totalViews;
    private Integer totalApplications;
    private LocalDate createdAt;
    public boolean isExpired(){
        return applicationDeadline.isBefore(LocalDate.now());
    }
}

