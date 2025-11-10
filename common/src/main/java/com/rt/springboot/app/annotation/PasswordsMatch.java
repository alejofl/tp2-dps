package com.rt.springboot.app.annotation;

import com.rt.springboot.app.validation.PasswordsMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by ankur on 23-10-2016.
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
@Repeatable(PasswordsMatch.List.class)
public @interface PasswordsMatch {
    String password() default "password";
    String confirmPassword() default "confirmPassword";

    boolean showErrorOnConfirmPassword() default true;

    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        PasswordsMatch[] value();
    }
}
