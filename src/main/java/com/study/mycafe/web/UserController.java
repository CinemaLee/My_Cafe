package com.study.mycafe.web;

import com.study.mycafe.repository.UserRepository;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }


    @GetMapping("/register")
    public String registerForm() {
        return "user/registerForm";
    }


    @PostMapping("/register")
    public String register(User user) {

        userRepository.save(user);


        return "redirect:/user/list"; // url
    }


    @GetMapping("/list")
    public String list(Model model) {
        List<User> all = userRepository.findAll();
        model.addAttribute("users",all);
        return "user/list"; // templates
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        model.addAttribute("users",user);
        return "user/updateForm";
    }
}
