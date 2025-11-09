package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.annotation.Adapter;
import com.rt.springboot.app.port.driving.user.FindUserByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;

@Adapter
@RequiredArgsConstructor
@SessionAttributes("user")
public class UserMvcAdapter {
    private final FindUserByUsernameUseCase findByUsernameUseCase;
    private final CreateUserUseCase createUserUseCase;

    private final MessageSource messageSource;

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Principal principal,
            Model model,
            RedirectAttributes flash,
            Locale locale
    ) {
        if (principal != null) {
            flash.addAttribute("info", messageSource.getMessage("text.login.already", null, locale));
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
        }
        if (logout != null) {
            model.addAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
        }

        return "login";
    }

    @GetMapping("/signup")
    public String showRegistrationForm(
            Model model
    ) {
        final var user = new CreateUserDto();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUserAccount(
            @Valid CreateUserDto user,
            BindingResult result,
            Model model,
            RedirectAttributes flash,
            Locale locale,
            Principal principal,
            Errors errors
    ) {
        if (findByUsernameUseCase.findByUsername(user.getUsername()) != null ) {
            model.addAttribute("warning", messageSource.getMessage("text.signup.existe", null, locale));
            return "signup";
        }

        // TODO expand to fields
        createUserUseCase.create(user);

        flash.addFlashAttribute("success", messageSource.getMessage("text.signup.flash.crear.success", null, locale));
        return "redirect:/login";
    }
}
