package com.study.mycafe.web;

import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.NotMatchIdException;
import com.study.mycafe.repository.QuestionRepository;
import com.study.mycafe.repository.UserRepository;
import com.study.mycafe.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/questionForm")
    public String questionForm(HttpSession session, Model model) {
        try {
            SessionUtils.isLoginUser(session);
            return "qna/qnaForm";
        }catch (IllegalStateException e) { // 로그인해야합니다.
            model.addAttribute("errorMessage",e.getMessage());
            return "user/login";
        }

    }


    @PostMapping("/create")
    public String create(String title, String contents, HttpSession session){

        User sessionUser = SessionUtils.getUserFromSession(session);

        questionRepository.save(Question.createQuestion(sessionUser,title,contents));

        return "redirect:/index";
    }



    @GetMapping("/{id}/questionShow")
    public String show(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseGet(null);
        model.addAttribute("question",question);
        return "qna/qnaShow";
    }



    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            SessionUtils.isLoginUser(session); // 비로그인이면 에러
            User loginUser = SessionUtils.getUserFromSession(session); // 로그인 세션 가져옴
            Question question = questionRepository.getOne(id); //  질문을 가져온다. 질문이 없을 수는 없다.
            question.isSameUser(loginUser); // 질문한 사람과 로그인한 사람이 다르면 에러
            model.addAttribute("question",question);
            return "qna/updateForm";

        }catch (IllegalStateException e){ // 로그인해야합니다.
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";

        }catch (NotMatchIdException e) { // 해당 유저가 아닙니다.
            Question question = questionRepository.getOne(id); // 에러메시지를 리스트에 뿌리기 위해 다시 값을 가져옴
            model.addAttribute("question",question);
            model.addAttribute("errorMessage",e.getMessage());
            return "qna/qnaShow";
        }


    }



    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, String title, String contents) {

        questionService.update(id,title,contents);

        return String.format("redirect:/questions/%d/questionShow",id);
    }



    @PostMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        try {
            SessionUtils.isLoginUser(session); // 비로그인이면 에러
            User loginUser = SessionUtils.getUserFromSession(session); // 로그인 세션 가져옴
            Question question = questionRepository.getOne(id); //  질문을 가져온다. 질문이 없을 수는 없다.
            question.isSameUser(loginUser); // 질문한 사람과 로그인한 사람이 다르면 에러
            questionRepository.deleteById(id);
            return "redirect:/index";

        }catch (IllegalStateException e){ // 로그인해야합니다.
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";

        }catch (NotMatchIdException e) { // 해당 유저가 아닙니다.
            Question question = questionRepository.getOne(id); // 에러메시지를 리스트에 뿌리기 위해 다시 값을 가져옴
            model.addAttribute("question",question);
            model.addAttribute("errorMessage",e.getMessage());
            return "qna/qnaShow";
        }



    }







}
