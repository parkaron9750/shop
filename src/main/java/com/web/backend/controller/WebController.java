package com.web.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String hello(){
        return "main";
    }


    @GetMapping("/test")
    public String test(){
        return "layouts/layout";
    }

}
