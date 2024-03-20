package com.jobnet.user.validations;

import com.jobnet.user.models.enums.EVerificationStatus;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VerificationStatusValidator.class)
public @interface VerificationStatus {
    String message() default "Verification Status must be one of: Verified, Pending, NotVerified, FailedVerification, Expired.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class VerificationStatusValidator  implements ConstraintValidator<VerificationStatus, String> {
    private static final List<String> ALLOWED_APPLICATION_STATUSES =
        Arrays.stream(EVerificationStatus.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_APPLICATION_STATUSES.contains(value);
    }
}