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
@Constraint(validatedBy = ResumeSupportPermissionValidator.class)
public @interface SupportPermission {
    String message() default "Resume support permission must be one of: Enable, Disable, UnderReview.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ResumeSupportPermissionValidator implements ConstraintValidator<SupportPermission, String> {
    private static final List<String> ALLOWED_RESUME_SUPPORT_PERMISSION =
        Arrays.stream(Resume.ESupportPermission.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_RESUME_SUPPORT_PERMISSION.contains(s);
    }
}