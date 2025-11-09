package com.rt.springboot.app.adapter.driving;

import com.rt.springboot.app.annotation.Adapter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Adapter
public class LocaleMvcAdapter {
    @GetMapping("/locale")
    public String locale(HttpServletRequest request) {
        String lastUrl  = request.getHeader("referer");
        return "redirect:".concat(lastUrl);
    }
}
