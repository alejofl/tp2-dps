package com.rt.springboot.app.validation;

import com.rt.springboot.app.annotation.PasswordsMatch;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordsMatchValidatorTest {

    private PasswordsMatchValidator validator;

    @Mock
    private PasswordsMatch annotation;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private ConstraintViolationBuilder violationBuilder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        validator = new PasswordsMatchValidator();

        when(annotation.password()).thenReturn("password");
        when(annotation.confirmPassword()).thenReturn("confirmPassword");
        when(annotation.showErrorOnConfirmPassword()).thenReturn(true);

        validator.initialize(annotation);

        when(context.getDefaultConstraintMessageTemplate()).thenReturn("Passwords do not match");
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
        when(nodeBuilder.addConstraintViolation()).thenReturn(context);
    }

    @Test
    @DisplayName("Should return true when passwords match")
    void shouldReturnTrueWhenPasswordsMatch() {
        PasswordDto dto = new PasswordDto("1234", "1234");
        boolean result = validator.isValid(dto, context);
        assertTrue(result);
        verify(context, never()).buildConstraintViolationWithTemplate(anyString());
    }

    @Test
    @DisplayName("Should return false when passwords do not match")
    void shouldReturnFalseWhenPasswordsDoNotMatch() {
        PasswordDto dto = new PasswordDto("1234", "abcd");
        when(context.getDefaultConstraintMessageTemplate()).thenReturn("Passwords do not match");
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(anyString())).thenReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext.class));

        boolean result = validator.isValid(dto, context);
        assertFalse(result);
        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("Passwords do not match");
    }

    @Test
    @DisplayName("Should return false when field does not exist")
    void shouldReturnFalseWhenFieldDoesNotExist() {
        class BadDto {
            final String onlyPassword = "test";
        }

        BadDto dto = new BadDto();
        boolean result = validator.isValid(dto, context);
        assertFalse(result);

        verify(context).disableDefaultConstraintViolation();
        verify(context).buildConstraintViolationWithTemplate("Passwords do not match");
    }

    @Test
    @DisplayName("Should return false without adding violation when show error is false")
    void shouldReturnFalseWithoutAddingViolationWhenShowErrorIsFalse() {
        when(annotation.showErrorOnConfirmPassword()).thenReturn(false);
        validator.initialize(annotation);

        PasswordDto dto = new PasswordDto("abc", "xyz");
        boolean result = validator.isValid(dto, context);

        assertFalse(result);
        verify(context, never()).buildConstraintViolationWithTemplate(anyString());
    }

    record PasswordDto(String password, String confirmPassword) {
    }
}
