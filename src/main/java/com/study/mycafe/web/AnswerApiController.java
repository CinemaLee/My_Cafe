package com.study.mycafe.web;

import com.study.mycafe.domain.Answer;
import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import com.study.mycafe.repository.AnswerRepository;
import com.study.mycafe.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session, Model model) {
        try {
            SessionUtils.isLoginUser(session);
            User loginUser = SessionUtils.getUserFromSession(session);
            Question question = questionRepository.getOne(questionId);
            answerRepository.save(Answer.createAnswer(loginUser, question, contents));
            return String.format("redirect:/questions/%d/questionShow", questionId);

        }catch (IllegalStateException e){ // 로그인해야합니다.
            model.addAttribute("errorMessage",e.getMessage());
            return "user/login";
        }
    }
}
