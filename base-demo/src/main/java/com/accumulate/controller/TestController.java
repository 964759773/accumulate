package com.accumulate.controller;

import com.accumulate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String getAll() {
        return userService.getAll();
    }
}
