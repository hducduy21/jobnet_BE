package com.jobnet.business.mappers;

import com.jobnet.business.dtos.responses.BusinessResponse;
import com.jobnet.business.models.Business;
import com.jobnet.clients.business.BusinessRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BusinessMapper {
	public Function<BusinessRegisterRequest, Business> convertToBusiness =
		request ->
			Business.builder()
				.name(request.name())
				.emailDomain(request.emailDomain())
				.build();

	public Function<Business, BusinessResponse> convertToBusinessResponse =
		business -> BusinessResponse.builder()
			.id(business.getId())
			.name(business.getName())
			.emailDomain(business.getEmailDomain())
			.type(business.getType())
			.description(business.getDescription())
			.phone(business.getPhone())
			.country(business.getCountry())
			.website(business.getWebsite())
			.employeeQuantity(business.getEmployeeQuantity())
			.foundedYear(business.getFoundedYear())
			.locations(business.getLocations())
			.profileImageId(business.getProfileImageId())
			.backgroundImageId(business.getBackgroundImageId())
			.createdAt(business.getCreatedAt())
			.status(business.getStatus())
			.followers(business.getFollowers())
			.totalFollowers(business.getTotalFollowers())
			.isDeleted(business.getIsDeleted())
			.build();
}
