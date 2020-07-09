package com.github.xrapalexandra.kr.web.controller;

import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "redirect:/error";
    }
}
