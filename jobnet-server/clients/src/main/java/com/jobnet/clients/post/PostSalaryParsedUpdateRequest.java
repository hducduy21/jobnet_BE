package com.jobnet.clients.post;

import java.math.BigInteger;

public record PostSalaryParsedUpdateRequest(
	BigInteger minSalary,
	BigInteger maxSalary
) {}
