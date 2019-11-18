package com.study.mycafe.web;

import com.study.mycafe.dto.UserDto;
import com.study.mycafe.exception.NotMatchIdException;
import com.study.mycafe.exception.NotMatchPasswordException;
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


    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        try{
            SessionUtils.isLoginUser(session);
            return "user/profile";
        }catch (IllegalStateException e) { // 로그인을 해야합니다.
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }


    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }


    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {

        try{
            User user = userRepository.findByUserId(userId);
            userService.loginLogic(user, password); // 아이디가 없으면 에러 / 비밀번호가 틀리면 에러
            session.setAttribute(SessionUtils.USER_SESSION_KEY, user); //loginUser. << SESSION_KEY
            log.info("Login Success!");
            return "redirect:/index";

        }catch (IllegalStateException  | NotMatchPasswordException e) { // 존재하지 않는 아이디 or 비밀번호가 다른경우.
            model.addAttribute("errorMessage", e.getMessage());
            log.info("Login Fail.");
            return "user/login"; // model을 넘기려면 redirect하면 안되고 템플릿을 리턴해줘야 넘어감.

        }


    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SessionUtils.USER_SESSION_KEY);
        return "redirect:/index";
    }



    @GetMapping("/registerForm")
    public String registerForm() {
        return "user/registerForm";
    }




    @PostMapping("/register")
    public String register(@RequestParam String userId, User user, Model model) {

        if(!(userRepository.findByUserId(userId)==null)){
            model.addAttribute("errorMessage", "이미 존재하는 아이디 입니다.");
            return "user/registerForm";
        }

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

        try{

            SessionUtils.isLoginUser(session);
            User sessionUser = SessionUtils.getUserFromSession(session);  // 세션 유저를 가져오고
            sessionUser.matchId(id); // 이 아이가 수정하려는 놈의 id와 같은지 판단
            model.addAttribute("users",sessionUser); // form에 세션유저 데이터를 넘겨주자.
            return "user/updateForm";

        }catch (IllegalStateException e) { // 로그인을 해야합니다.
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";

        }catch (PersonNotFoundException | NotMatchIdException e) { // 해당 사용자가 존재하지 않는 경우 / 해당 사용자가 아닌경우.
            List<User> all = userRepository.findAll();
            model.addAttribute("users",all); // 에러메세지가 있을때 리스트를 다시보여주기 위해 보내주는 것.
            model.addAttribute("errorMessage", e.getMessage());
            return "user/list";

        }

    }


    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, UserDto userDto){ // 수정된 form데이터가 UserDto에 담김. @ModelAttribute("폼이름") UserForm form .. 요런식으로 받기도 가능.


        userService.updateUser(id, userDto.getUserId(), userDto.getName(), userDto.getEmail(), userDto.getPassword());

        return "redirect:/user/list";
    }

}
