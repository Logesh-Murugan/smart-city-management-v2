package com.smartcity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            switch (statusCode) {
                case 404:
                    model.addAttribute("errorTitle", "Page Not Found");
                    model.addAttribute("errorMessage", "The page you are looking for could not be found.");
                    break;
                case 403:
                    model.addAttribute("errorTitle", "Access Denied");
                    model.addAttribute("errorMessage", "You don't have permission to access this resource.");
                    break;
                case 500:
                    model.addAttribute("errorTitle", "Internal Server Error");
                    model.addAttribute("errorMessage", "Something went wrong on our end. Please try again later.");
                    break;
                default:
                    model.addAttribute("errorTitle", "Error " + statusCode);
                    model.addAttribute("errorMessage", "An unexpected error occurred.");
                    break;
            }
            
            model.addAttribute("statusCode", statusCode);
        } else {
            model.addAttribute("errorTitle", "Unknown Error");
            model.addAttribute("errorMessage", "An unexpected error occurred.");
            model.addAttribute("statusCode", "Unknown");
        }
        
        return "error";
    }
}
