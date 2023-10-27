package com.demo.webauthn.controller;

import com.demo.webauthn.service.LoginService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @ResponseBody
    public String startLogin(@RequestParam String username, HttpSession session) {

        return loginService.startLogin(username, session);
    }

    @PostMapping("/finish")
    public String finishLogin(@RequestParam String credential, @RequestParam String username, Model model,
                              HttpSession session) {

        return loginService.finishLogin(credential, username, model, session);
    }
}
