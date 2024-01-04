package com.log4j2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log4j2")
public class Log4j2Controller {

    @GetMapping("")
    public void log4j2() {
        int n = 1/0;
    }

}
