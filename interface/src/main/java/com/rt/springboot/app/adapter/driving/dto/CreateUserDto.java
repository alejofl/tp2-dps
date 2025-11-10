package com.rt.springboot.app.adapter.driving.dto;

import com.rt.springboot.app.annotation.PasswordsMatch;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@PasswordsMatch
public class CreateUserDto {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotNull
    @AssertTrue
    private boolean enabled;
}
