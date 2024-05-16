package com.drip.controller;

import com.drip.domain.User;
import com.drip.service.BlogLoginService;
import com.drip.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return blogLoginService.login(user);
    }
}
