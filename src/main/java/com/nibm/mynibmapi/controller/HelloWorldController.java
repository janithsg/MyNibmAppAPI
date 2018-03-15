package com.nibm.mynibmapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(name = "/")
    public String sayHello(){
        return "<html><body><h3 style=\"color:blue\">NIBM Mobile Application Development Module Project. <br>Group Proxima<br>Developed this REST API with SpringMVC by Janith Ganewatta :janithsg@gmail.com</h3></body></html>";
    }
}
