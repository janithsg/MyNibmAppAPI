package com.nibm.mynibmapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloworld/")
public class HelloWorldController {

    @RequestMapping(name = "/")
    public String sayHello(){
        return "Hello World Working...";
    }
}
