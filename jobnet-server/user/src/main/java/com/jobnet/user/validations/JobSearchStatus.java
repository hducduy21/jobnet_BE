package com.jobnet.user.validations;

import com.jobnet.user.models.enums.EJobSearchStatus;
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
@Constraint(validatedBy = JobSearchStatusValidator.class)
public @interface JobSearchStatus {
    String message() default "Job search status must be one of: NotActivelySearch, ActivelySearch, OnHold, AwaitingResponse";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class JobSearchStatusValidator implements ConstraintValidator<JobSearchStatus, String> {
    private static final List<String> ALLOWED_APPLICATION_STATUSES =
        Arrays.stream(EJobSearchStatus.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_APPLICATION_STATUSES.contains(value);
    }

}
