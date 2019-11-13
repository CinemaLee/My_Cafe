package com.study.mycafe.web;

import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import com.study.mycafe.repository.QuestionRepository;
import com.study.mycafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/findUserId")
    public String findUserIdList(@RequestParam String term, Model model) {
        User user = userRepository.findByUserId(term);
        List<Question> questions = questionRepository.findByUser(user);
        model.addAttribute("questions", questions);
        return "index";
    }


    @GetMapping("index")
    public String index(Model model) {

        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("")
    public String home(Model model) {
        return "firstIndex";
    }
}
