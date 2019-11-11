package com.study.mycafe.web;

import com.study.mycafe.dto.UserDto;
import com.study.mycafe.repository.UserRepository;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.PersonNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {

        User user = userRepository.findByUserId(userId);

        if(user == null) {
            log.info("Login Fail.");
            model.addAttribute("message","존재하지 않는 아이디입니다.");
            return "redirect:/user/loginForm";
        }

        if(!user.getPassword().equals(password)){
            log.info("Login Fail.");
            model.addAttribute("message","비밀번호가 틀렸습니다.");
            return "redirect:/user/loginForm";
        }
        log.info("Login Success!!");
        log.info("LoginUser : >> "+user);
        session.setAttribute("loginUser",user); // 세션에 저장.



        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        return "redirect:/";
    }



    @GetMapping("/registerForm")
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
        List<User> all = userRepository.findAll(); // 이럴경우 굳이 service를 활용해야 하는가에 대해 고민해봐도 괜찮다.
        model.addAttribute("users",all);
        return "user/list"; // templates
    }


    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model) {

        User user = userRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        model.addAttribute("users",user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable Long id, UserDto userDto){

        User userAtDb = userRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        userAtDb.setUserId(userDto.getUserId());
        userAtDb.setName(userDto.getName());
        userAtDb.setEmail(userDto.getEmail());
        userAtDb.setPassword(userDto.getPassword());
        userRepository.save(userAtDb);

        return "redirect:/user/list";
    }

}
