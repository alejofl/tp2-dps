package com.rt.springboot.app.validation;

import com.rt.springboot.app.annotation.PasswordsMatch;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {
    private PasswordsMatch config;

    @Override
    public void initialize(PasswordsMatch config) {
        this.config = config;
    }

    @Override
    public boolean isValid(Object passwordDto, ConstraintValidatorContext context) {
        boolean isValid = PasswordsMatchValidator.matchPassword(passwordDto, config.password(), config.confirmPassword());

        if (!isValid && config.showErrorOnConfirmPassword()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(config.confirmPassword()).addConstraintViolation();
        }

        return isValid;
    }

    private static boolean matchPassword(Object passwordDto, String password, String confirmPassword)  {
        try {
            Field passwordField = passwordDto.getClass().getDeclaredField(password);
            Field confirmPasswordField = passwordDto.getClass().getDeclaredField(confirmPassword);
            passwordField.setAccessible(true);
            confirmPasswordField.setAccessible(true);
            Object passwordValue = passwordField.get(passwordDto);
            Object confirmPasswordValue = confirmPasswordField.get(passwordDto);
            passwordField.setAccessible(false);
            confirmPasswordField.setAccessible(false);
            return Objects.equals(passwordValue, confirmPasswordValue);
        } catch (NoSuchFieldException | IllegalAccessException ex){
            return false;
        }
    }
}
