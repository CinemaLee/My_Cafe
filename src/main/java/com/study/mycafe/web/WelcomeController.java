package com.study.mycafe.web;

import com.study.mycafe.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String welcome(Model model) {
    model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }
}
