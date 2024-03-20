package com.jobnet.common.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;


public record PostElasticResponse(
	String id,
	String title,
	Profession profession,
	Category category,
	BigInteger minSalary,
	String minSalaryString,
	BigInteger maxSalary,
	String maxSalaryString,
	String currency,
	Level level,
	List<Location> locations,
	Business business,
	String workingFormat,
	@JsonDeserialize(using = LocalDateDeserializer.class)
	LocalDate applicationDeadline,
	@JsonDeserialize(using = LocalDateDeserializer.class)
	LocalDate createdAt
) {
public record Level(
	String id,
	String name
) {
}
public record Business(
	String id,
	String name,
	String profileImageId
) {
}

public record Profession(
	String id,
	String name
) {
}

public record Category(
	String id,
	String name
) {
}

public record Location(
	String provinceName,
	String specificAddress
) {
}
}

