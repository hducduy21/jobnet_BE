package com.jobnet.resume.validations;

import com.jobnet.resume.models.Resume;
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
@Constraint(validatedBy = ResumeAccessValidator.class)
public @interface AccessPermission {
    String message() default
        "Resume access status must be one of: Private, Public, OnlyRegisteredEmployers, OnlyVerifiedRecruiters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ResumeAccessValidator implements ConstraintValidator<AccessPermission, String> {
    private static final List<String> ALLOWED_RESUME_ACCESS =
        Arrays.stream(Resume.EAccessPermission.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String e, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_RESUME_ACCESS.contains(e);
    }
}