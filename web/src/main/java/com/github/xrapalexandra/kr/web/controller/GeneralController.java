package com.github.xrapalexandra.kr.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class GeneralController {

    @GetMapping("/")
    public String getDefault() {
        return "welcome";
    }

    @GetMapping("/error")
    public String NotFoundPage(ModelMap model) {
        model.put("error", "Непредвиденная ошибка");
        return "messages";
    }
}
