package com.laya.ExcelVersion.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class OAuth2CallbackController {

    @RequestMapping("/oauth2/callback")
    public String login(@RequestParam String code, @RequestParam String state, @RequestParam String iss, @RequestParam String session_state) {
        log.info("OAuth2 callback received");
        return "redirect:/login/oauth2/code/epam?code=" + code + "&state=" + state + "&iss=" + iss + "&session_state=" + session_state;
    }

}