package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRest {
    
    @GetMapping("/")
    public String root() {
        return "Hello World NUEVO!!";
    }

}
