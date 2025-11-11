package com.rt.springboot.app.adapter.view;

import com.rt.springboot.app.adapter.driving.UserMvcAdapter;
import com.rt.springboot.app.adapter.driving.dto.CreateUserDto;

import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driving.user.CreateUserUseCase;
import com.rt.springboot.app.port.driving.user.FindUserByUsernameUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserMvcAdapterTest {

    @Mock private FindUserByUsernameUseCase findByUsernameUseCase;
    @Mock private CreateUserUseCase createUserUseCase;
    @Mock private MessageSource messageSource;

    @Mock private Model model;
    @Mock private RedirectAttributes flash;
    @Mock private Principal principal;
    @Mock private BindingResult mockBindingResult;

    @Mock private User mockUser;

    private UserMvcAdapter adapter;
    private Locale locale;

    @BeforeEach
    void setUp() {
        adapter = new UserMvcAdapter(
                findByUsernameUseCase,
                createUserUseCase,
                messageSource
        );
        locale = Locale.ENGLISH;
    }

    @Test
    void testLoginUserAlreadyLoggedIn() {
        String infoMsg = "Ya estás logueado";
        when(messageSource.getMessage(eq("text.login.already"), any(), eq(locale)))
                .thenReturn(infoMsg);

        String viewName = adapter.login(null, null, principal, model, flash, locale);

        assertEquals("redirect:/", viewName);
        verify(flash, times(1)).addAttribute("info", infoMsg);
        verifyNoInteractions(model);
    }

    @Test
    void testLoginWithError() {
        String errorMsg = "Error de login";
        when(messageSource.getMessage(eq("text.login.error"), any(), eq(locale)))
                .thenReturn(errorMsg);

        String viewName = adapter.login("true", null, null, model, flash, locale);

        assertEquals("login", viewName);
        verify(model, times(1)).addAttribute("error", errorMsg);
        verifyNoInteractions(flash);
    }

    @Test
    void testLoginWithLogout() {
        String successMsg = "Deslogueado";
        when(messageSource.getMessage(eq("text.login.logout"), any(), eq(locale)))
                .thenReturn(successMsg);

        String viewName = adapter.login(null, "true", null, model, flash, locale);

        assertEquals("login", viewName);
        verify(model, times(1)).addAttribute("success", successMsg);
        verifyNoInteractions(flash);
    }

    @Test
    void testLoginDefaultView() {
        String viewName = adapter.login(null, null, null, model, flash, locale);

        assertEquals("login", viewName);
        verifyNoInteractions(model);
        verifyNoInteractions(flash);
    }

    @Test
    void testShowRegistrationForm() {
        String viewName = adapter.showRegistrationForm(model);

        assertEquals("signup", viewName);
        verify(model, times(1)).addAttribute(eq("user"), any(CreateUserDto.class));
    }

    @Test
    void testRegisterUserWithValidationErrors() {
        when(mockBindingResult.hasErrors()).thenReturn(true);
        CreateUserDto userDto = new CreateUserDto();

        String viewName = adapter.registerUserAccount(userDto, mockBindingResult, model, flash, locale);

        assertEquals("signup", viewName);
        verifyNoInteractions(model);
        verifyNoInteractions(flash);
        verifyNoInteractions(findByUsernameUseCase);
        verifyNoInteractions(createUserUseCase);
    }

    @Test
    void testRegisterUserUserAlreadyExists() {
        String warningMsg = "Usuario ya existe";
        CreateUserDto userDto = new CreateUserDto();
        userDto.setUsername("testuser");

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(findByUsernameUseCase.findByUsername("testuser")).thenReturn(mockUser);
        when(messageSource.getMessage(eq("text.signup.existe"), any(), eq(locale)))
                .thenReturn(warningMsg);

        String viewName = adapter.registerUserAccount(userDto, mockBindingResult, model, flash, locale);

        assertEquals("signup", viewName);
        verify(findByUsernameUseCase, times(1)).findByUsername("testuser");
        verify(model, times(1)).addAttribute("warning", warningMsg);
        verifyNoInteractions(flash);
        verifyNoInteractions(createUserUseCase);
    }

    @Test
    void testRegisterUserSuccess() {
        String successMsg = "Usuario creado";
        CreateUserDto userDto = new CreateUserDto();
        userDto.setUsername("newuser");
        userDto.setPassword("pass123");

        when(mockBindingResult.hasErrors()).thenReturn(false);
        when(findByUsernameUseCase.findByUsername("newuser")).thenReturn(null);
        when(messageSource.getMessage(eq("text.signup.flash.crear.success"), any(), eq(locale)))
                .thenReturn(successMsg);

        String viewName = adapter.registerUserAccount(userDto, mockBindingResult, model, flash, locale);

        assertEquals("redirect:/login", viewName);
        verify(findByUsernameUseCase, times(1)).findByUsername("newuser");
        verify(createUserUseCase, times(1)).create("newuser", "pass123");
        verify(flash, times(1)).addFlashAttribute("success", successMsg);
        verifyNoInteractions(model);
    }
}