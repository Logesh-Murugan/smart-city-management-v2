package com.smartcity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaviconController {

    // Redirect favicon requests to an existing static placeholder image to avoid 500 errors
    @GetMapping("/favicon.ico")
    public String favicon() {
        return "redirect:/images/placeholder.svg";
    }
}
