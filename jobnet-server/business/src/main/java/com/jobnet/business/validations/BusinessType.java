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
@Constraint(validatedBy = TypeBusinessValidator.class)
public @interface BusinessType {
    String message() default "Type business must be one of: Product, Outsource.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class TypeBusinessValidator implements ConstraintValidator<BusinessType, String> {

    private static final List<String> ALLOWED_TYPE_BUSINESSES =
        Arrays.stream(Business.EType.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String e, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_TYPE_BUSINESSES.contains(e);
    }
}