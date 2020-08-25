package com.junho.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RequestMapping({"/hellouser"})
    public String helloUser() {
        return "hello User";
    }

    @RequestMapping({"/helloadmin"})
    public String helloAdmin() {
        return "Hello Admin";
    }
}
