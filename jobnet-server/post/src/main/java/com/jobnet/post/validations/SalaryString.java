package com.jobnet.post.validations;

import com.jobnet.post.utils.SalaryUtils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SalaryStringValidator.class)
public @interface SalaryString {
    String message() default "Salary string must be like the following format: '12xxxxxx', '12 triệu', '12.5 triệu'";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class SalaryStringValidator implements ConstraintValidator<SalaryString, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return SalaryUtils.checkFormat(s);
    }
}
