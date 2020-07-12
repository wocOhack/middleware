package com.woc.controller;

import com.woc.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.woc.service.UserService;
import com.woc.entity.Driver;
import com.woc.entity.Pricing;
import com.woc.entity.Rider;
import com.woc.entity.User;
import com.woc.service.WOCService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/woc")
public class MainController {

    @Autowired
    WOCService wocService;

    @Autowired
    UserService userService;

    @PostMapping("/addUserCredentials")
    @ResponseBody
    public void addUserCredentials(@RequestBody UserCredentials uc) {
        System.out.println("user cred request : " + uc.toString());
        userService.createUserCreds(uc);
    }
}