package com.jobnet.business.models;

import com.jobnet.common.dtos.LocationRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("businesses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString(of = {"id","name"})
public class Business {

    @Id
    private String id;
    private String name;
    private EType type;
    private String country;
    private Integer employeeQuantity;
    private Integer foundedYear;
    private String description;
    private String emailDomain;
    private String phone;
    private String website;
    private List<String> locations;
    private String profileImageId;
    private String backgroundImageId;
    private List<String> followers;
    private int totalFollowers;
    private LocalDateTime createdAt;
    private EStatus status;
    private Boolean isDeleted;

    public enum EType {
        Product,
        Outsource
    }

    public enum EStatus {
        Pending,
        Approved,
        Rejected
    }
}
