package com.rt.springboot.app.adapter.driving.dto;

import com.rt.springboot.app.annotation.PasswordsMatch;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
