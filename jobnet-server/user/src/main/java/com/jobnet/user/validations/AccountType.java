package com.jobnet.user.validations;

import com.jobnet.user.models.enums.EAccountType;
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
@Constraint(validatedBy = AccountTypeValidator.class)
public @interface AccountType {
    String message() default "Account Type must be one of: Free, Premium.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class AccountTypeValidator implements ConstraintValidator<AccountType, String> {

    private static final List<String> ALLOWED_ACCOUNT_TYPE =
        Arrays.stream(EAccountType.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_ACCOUNT_TYPE.contains(value);
    }

}