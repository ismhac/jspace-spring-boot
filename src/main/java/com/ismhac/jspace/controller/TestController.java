package com.ismhac.jspace.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
//@RequestMapping("")
public class TestController {
    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("{}",oAuth2User);
        return oAuth2User.getAttributes();
    }

//    @GetMapping("/user")
//    public String getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
//        return "hello";
//    }
}
