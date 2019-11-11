package com.study.mycafe.web;

import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.QuestionNotFoundException;
import com.study.mycafe.repository.QuestionRepository;
import com.study.mycafe.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/questionForm")
    public String questionForm(HttpSession session) {
        if(!SessionUtils.isLoginUser(session)){
            return "redirect:/user/loginForm";
        }
        return "qna/qnaForm";
    }


    @PostMapping("/create")
    public String create(String title, String contents, HttpSession session){

        User sessionUser = SessionUtils.getUserFromSession(session);

        questionRepository.save(Question.createQuestion(sessionUser,title,contents));

        return "redirect:/";
    }


    @GetMapping("/{id}/questionShow")
    public String show(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseGet(null);
        model.addAttribute("question",question);
        return "qna/qnaShow";
    }


    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if(!SessionUtils.isLoginUser(session)){
            return "redirect:/user/loginForm";
        }
        User loginUser = SessionUtils.getUserFromSession(session);
        System.out.println("sessionUser: >>" +loginUser);
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);

        if(!question.isSameUser(loginUser)) { // 내가 id던져줄께 너가 판단해라. // id가 같지 않으면~ (주소로 questions/2/updateForm 요런식으로 넘어오는 상황)
            throw new IllegalStateException("자신의 작성글만 수정할 수 있습니다.");
        }

        model.addAttribute("question",question);

        return "qna/updateForm";

    }


    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, String title, String contents) {

        questionService.update(id,title,contents);

        return String.format("redirect:/questions/%d/questionShow",id);
    }



    @PostMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if(!SessionUtils.isLoginUser(session)){
            return "redirect:/user/loginForm";
        }
        User sessionUser = SessionUtils.getUserFromSession(session);
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        if(!question.isSameUser(sessionUser)){ // 내가 id던져줄께 너가 판단해라. // id가 같지 않으면~ (주소로 questions/2/updateForm 요런식으로 넘어오는 상황)
            throw new IllegalStateException("자신의 작성글만 삭제할 수 있습니다.");
        }
        questionRepository.deleteById(id);

        return "redirect:/";
    }

}
