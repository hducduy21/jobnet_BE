package com.jobnet.post.dtos.requests;

import com.jobnet.common.dtos.LocationRequest;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostHeadingInfoUpdateRequest {

    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @NotNull(message = "Min salary string is required.")
    @NotBlank(message = "Min salary string cannot be blank.")
    private String minSalaryString;

    @NotNull(message = "Max salary string is required.")
    @NotBlank(message = "Max salary string cannot be blank.")
    private String maxSalaryString;

    @NotNull(message = "Currency is required.")
    @NotBlank(message = "Currency cannot be blank.")
    private String currency;

    @NotNull(message = "Locations is required.")
    private List<LocationRequest> locations;

    @NotNull(message = "Year of experience is required.")
    @NotBlank(message = "Year of experience cannot be blank.")
    private String yearsOfExperience;

    @NotNull(message = "Requisition number is required.")
    private Integer requisitionNumber;

    @NotNull(message = "Application deadline is required.")
    @Future(message = "Application deadline must be in future")
    private LocalDate applicationDeadline;

}
