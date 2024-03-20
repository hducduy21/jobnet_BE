package com.jobnet.post.dtos.requests;

import com.jobnet.post.validations.ActiveStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostActiveStatusUpdateRequest {

    @NotNull(message = "Active status is required.")
    @ActiveStatus
    private String activeStatus;
}
