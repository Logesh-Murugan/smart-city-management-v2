package com.smartcity.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateInputException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TemplateInputException.class)
    public ModelAndView handleTemplateException(TemplateInputException ex, HttpServletRequest request) {
        log.error("Template processing error on {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorTitle", "Template Error");
        mav.addObject("errorMessage", "There was an error processing the page template. Please contact the administrator.");
        mav.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error on {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorTitle", "Internal Server Error");
        mav.addObject("errorMessage", "Something went wrong on our end. Please try again later.");
        mav.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return mav;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("Runtime error on {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorTitle", "Application Error");
        mav.addObject("errorMessage", "An application error occurred. Please try again or contact support if the problem persists.");
        mav.addObject("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return mav;
    }
}
