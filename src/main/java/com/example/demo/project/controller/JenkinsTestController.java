package com.example.demo.project.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */
@RestController
public class JenkinsTestController {

    @RequestMapping("/hehe")
    public String message(){

        return "这是版本1.0";
    }


}
