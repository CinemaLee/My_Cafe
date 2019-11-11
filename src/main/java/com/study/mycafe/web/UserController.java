package com.study.mycafe.web;

import com.study.mycafe.dto.UserDto;
import com.study.mycafe.repository.UserRepository;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.PersonNotFoundException;
import com.study.mycafe.service.UserService;
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

    @Autowired
    private UserService userService;


    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {

        User user = userRepository.findByUserId(userId);

        if(!userService.loginLogic(user, password)) {
            log.info("Login Fail.");
            return "redirect:/user/loginForm";
        }

        log.info("Login Success!");

        session.setAttribute(SessionUtils.USER_SESSION_KEY,user); // 세션에 저장.

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtils.USER_SESSION_KEY);
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
    public String updateForm(@PathVariable Long id, Model model,HttpSession session) {

        if(!SessionUtils.isLoginUser(session)) { // 로그인 되있는 사용자만 updateForm 띄워줄거야.
            return "redirect:/user/loginForm";
        }

        User sessionUser = SessionUtils.getUserFromSession(session);  // 세션 유저를 가져오고 이 아이가 수정하려는 놈의 id와 같은지 판단
        if(!sessionUser.matchId(id)){ // 내가 id던져줄께 너가 판단해라. // id가 같지 않으면~ (주소로 user/2/updateForm 요런식으로 넘어오는 상황)
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        model.addAttribute("users",sessionUser); // form에 세션유저 데이터를 넘겨주자.
        return "user/updateForm";
    }


    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, UserDto userDto){ // 수정된 form데이터가 UserDto에 담김. @ModelAttribute("폼이름") UserForm form .. 요런식으로 받기도 가능.


        userService.updateUser(id, userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());

        return "redirect:/user/list";
    }

}
