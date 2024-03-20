package com.jobnet.business.validations;

import com.jobnet.business.models.Business;
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
@Constraint(validatedBy = StatusValidator.class)
public @interface BusinessStatus {
    String message() default "Status must be one of: Pending, Approved, Rejected";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class StatusValidator implements ConstraintValidator<BusinessStatus, String> {

    private static final List<String> ALLOWED_STATUSES =
        Arrays.stream(Business.EStatus.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String e, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_STATUSES.contains(e);
    }
}