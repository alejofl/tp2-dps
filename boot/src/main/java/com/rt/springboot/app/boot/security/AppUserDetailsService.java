package com.rt.springboot.app.boot.security;

import com.rt.springboot.app.model.Role;
import com.rt.springboot.app.port.driving.user.FindUserByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final FindUserByUsernameUseCase findUserByUsernameUseCase;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final var user = findUserByUsernameUseCase.findByUsername(s);
        if (user == null || user.authorities().isEmpty()) {
            throw new UsernameNotFoundException("Username " + s + " not found");
        }

        final var authorities = user
                .authorities()
                .stream()
                .map(Role::authority)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new User(
            user.username(),
            user.password(),
            user.enabled(),
            true,
            true,
            true,
            authorities
        );
    }
}
