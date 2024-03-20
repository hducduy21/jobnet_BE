package com.jobnet.application.validations;

import com.jobnet.application.models.Application;
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
@Constraint(validatedBy = ApplicationStatusValidator.class)
public @interface ApplicationStatus {
    String message() default "Application status must be one of: Submitted, Reviewed, Rejected, Hired";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ApplicationStatusValidator implements ConstraintValidator<ApplicationStatus, String> {

    private static final List<String> ALLOWED_APPLICATION_STATUSES =
        Arrays.stream(Application.EApplicationStatus.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_APPLICATION_STATUSES.contains(value);
    }

}