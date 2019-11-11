package com.study.mycafe.web;

import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.Result;
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
        Question question = questionRepository.getOne(id);
        Result result = validPermission(session, question);
        if(!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        model.addAttribute("question", question);
        return "qna/updateForm";

    }



    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, String title, String contents) {

        questionService.update(id,title,contents);

        return String.format("redirect:/questions/%d/questionShow",id);
    }



    @PostMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.getOne(id);
        Result result = validPermission(session, question);

        if(!result.isValid()){
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }
        questionRepository.deleteById(id);
        return "redirect:/";

    }


    private Result validPermission(HttpSession session, Question question) {

        if(!SessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User loginUser = SessionUtils.getUserFromSession(session);
        if(!question.isSameUser(loginUser)) {
            return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
        }

        return Result.ok();

    }


//    private void hasPermission(HttpSession session, Question question) {
//
//        if(!SessionUtils.isLoginUser(session)) {
//            throw new IllegalStateException("로그인이 필요합니다.");
//        }
//        User loginUser = SessionUtils.getUserFromSession(session);
//        if(!question.isSameUser(loginUser)) {
//            throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");
//        }
//

//    }


}
