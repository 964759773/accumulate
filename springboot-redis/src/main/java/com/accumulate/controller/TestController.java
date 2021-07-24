package com.accumulate.controller;

import com.accumulate.annotation.AccessLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    @AccessLimit
    public String test() {

        return "ok";
    }

}
