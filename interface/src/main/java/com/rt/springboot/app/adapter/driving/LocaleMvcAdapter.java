package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.annotation.DrivingAdapter;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@DrivingAdapter
public class LocaleMvcAdapter {
    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String lastUrl  = request.getHeader("referer");
        return "redirect:".concat(lastUrl);
    }
}
