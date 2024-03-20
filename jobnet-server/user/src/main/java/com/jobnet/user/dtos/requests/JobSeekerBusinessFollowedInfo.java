package com.jobnet.user.dtos.requests;

import com.jobnet.user.models.enums.EFollowAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JobSeekerBusinessFollowedInfo {

	@NotNull(message = "{validation.status.notNull}")
	private EFollowAction status;

	@NotBlank(message = "{validation.businessId.notBlank}")
	private String businessId;
}
