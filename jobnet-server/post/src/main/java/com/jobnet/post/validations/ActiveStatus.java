package com.jobnet.post.validations;

import com.jobnet.post.models.Post;
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
@Constraint(validatedBy = PostStatusValidator.class)
public @interface ActiveStatus {
    String message() default "Active status must be one of: Open, Stop, Close.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class PostStatusValidator implements ConstraintValidator<ActiveStatus, String> {

    private static final List<String> ALLOWED_POST_STATUS =
        Arrays.stream(Post.EActiveStatus.values())
            .map(Enum::name)
            .toList();

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return ALLOWED_POST_STATUS.contains(value);
    }

}