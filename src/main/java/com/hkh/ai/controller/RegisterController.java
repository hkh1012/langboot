package com.hkh.ai.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 注册
 */
@Controller
@AllArgsConstructor
public class RegisterController {

    @GetMapping("/registerUI")
    public String registerUi(Model model) {
        return "register";
    }


    @PostMapping("/register")
    public String register(){
        return "index";
    }
}
