package com.study.mycafe.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/helloworld")
    public String welcome(String name, int age, Model model) {
        System.out.println("name: "+name +"age: "+age);
        model.addAttribute("name",name); // 웹에서 넘어온 값을 welcome.html로 전달한다.
        model.addAttribute("age",age);
        return "welcome";
    }
}
