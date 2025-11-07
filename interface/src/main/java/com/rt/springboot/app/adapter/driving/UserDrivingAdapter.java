package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.model.User;
import com.rt.springboot.app.port.driving.user.FindByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserDrivingAdapter {

    private final FindByUsernameUseCase findByUsernameUseCase;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(findByUsernameUseCase.findByUsername(username));
    }

}
