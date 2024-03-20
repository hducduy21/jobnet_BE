package com.jobnet.post.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jobnet.common.dtos.LocationRequest;
import com.jobnet.post.validations.File;
import com.jobnet.post.validations.SalaryString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @NotNull(message = "Profession ID is required.")
    @NotBlank(message = "Profession ID cannot be blank.")
    private String professionId;

    @NotNull(message = "Min salary string is required.")
    @NotBlank(message = "Min salary string cannot be blank.")
    @Length(min = 7, message = "Min salary length must be greater than 6.")
    @SalaryString
    private String minSalaryString;

    @NotNull(message = "Max salary string is required.")
    @NotBlank(message = "Max salary string cannot be blank.")
    @Length(min = 7, message = "Max salary length must be greater than 6.")
    @SalaryString
    private String maxSalaryString;

    @NotNull(message = "Currency is required.")
    @NotBlank(message = "Currency cannot be blank.")
    private String currency;

    @NotNull(message = "Level ID is required.")
    @NotBlank(message = "Level ID cannot be blank.")
    private String levelId;

    @NotNull(message = "Locations is required.")
    @NotEmpty(message = "Locations cannot be empty.")
    private List<LocationRequest> locations;

    @NotNull(message = "Working format is required.")
    @NotBlank(message = "Working format cannot be blank.")
    private String workingFormat;

    @NotNull(message = "Benefit IDs is required.")
    @NotEmpty(message = "Benefit IDs cannot be empty.")
    private List<String> benefitIds;

    @NotNull(message = "Description is required.")
    @NotBlank(message = "Description cannot be blank.")
    private String description;

    @NotNull(message = "Years of experience is required.")
    @NotBlank(message = "Years of experience cannot be blank.")
    private String yearsOfExperience;

    @NotNull(message = "Other requirements is required.")
    @NotBlank(message = "Other requirements cannot be blank.")
    private String otherRequirements;

    @NotNull(message = "Requisition number is required.")
    private Integer requisitionNumber;

    @NotNull(message = "Application deadline is required.")
//    @Future(message = "Application deadline must be in future")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String applicationDeadline;

    @File(message = "JD file is required.")
    private MultipartFile jdFile;
}
