package com.jobnet.user.validations;

import com.jobnet.user.models.enums.EGender;
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
@Constraint(validatedBy = GenderValidator.class)

public @interface Gender {
    String message() default "Gender is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class GenderValidator implements ConstraintValidator<Gender, String> {
    private static final List<String> ALLOWED_GENDERS =
        Arrays.stream(EGender.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_GENDERS.contains(value);
    }
}
