package com.sun.web.restful_web_service.homepage;

import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController 
public class HomePageController {

    private MessageSource messageSource;

    public HomePageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @GetMapping("/home-page")
    public HomePageResponse home() {
        return new HomePageResponse("Welcome to the RESTful Web Service!");
    }


     @GetMapping(path = "/home-page-string")
    public String homeString() {
        return "Welcome to the RESTful Web Service!";
    }

    @GetMapping("/home-page/path-var/{name}")
    public HomePageResponse getMethodName(@PathVariable String name) {
        return new HomePageResponse("Welcome to the RESTful Web Service! " + name);
    }

     @GetMapping("/hello-world-internationalized")
    public String homeInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
        
    }
    
}
