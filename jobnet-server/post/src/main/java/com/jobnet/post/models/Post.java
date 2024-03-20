package com.jobnet.post.models;

import com.jobnet.common.dtos.LocationRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Document("posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Post {

    @Id
    private String id;
    private String title;
    private Profession profession;
    private BigInteger minSalary;
    private String minSalaryString;
    private BigInteger maxSalary;
    private String maxSalaryString;
    private String currency;
    private Level level;
    private List<LocationRequest> locations;
    private String workingFormat;
    private List<Benefit> benefits;
    private String description;
    private String yearsOfExperience;
    private String otherRequirements;
    private Integer requisitionNumber;
    private LocalDate applicationDeadline;
    private String jdId;
    private String recruiterId;
    private Business business;
    private EActiveStatus activeStatus;
    private Integer totalViews;
    private Integer totalApplications;
    private LocalDate createdAt;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Profession {
        private String id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Level {
        private String id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Benefit {
        private String id;
        private String name;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class Business {
        private String id;
        private String name;
        private String profileImageId;
    }

    public enum EActiveStatus {
        Pending,
        Opening,
        Stopped,
        Closed,
        Blocked,
        Rejected
    }
}
