package com.study.mycafe.web;

import com.study.mycafe.domain.Answer;
import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.NotMatchIdException;
import com.study.mycafe.exception.QuestionNotFoundException;
import com.study.mycafe.repository.AnswerRepository;
import com.study.mycafe.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * api Controller로 만들어보자.
 */

@Controller // RestController : 반환값을 자동으로 json형태로 변환해줌.
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerApiController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session,Model model) {
        try {
            SessionUtils.isLoginUser(session);
            User loginUser = SessionUtils.getUserFromSession(session);
            Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
            Answer answer = Answer.createAnswer(loginUser, question, contents.replace("\r\n","<br>"));
            question.addAnswerCount();
            answerRepository.save(answer);
            return String.format("redirect:/questions/%d/questionShow",questionId); // save는 그 엔티티 자체를 반환한다. 원래 이러면 안되고 DTO를 만들어야해.

        }catch (IllegalStateException e){ // 로그인해야합니다.
            model.addAttribute("errorMessage",e.getMessage());
            return "user/login";
        }
    }


    @PostMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session, Model model) {
        try {
            SessionUtils.isLoginUser(session);
            User loginUser = SessionUtils.getUserFromSession(session);
            Question question = questionRepository.getOne(questionId);
            Answer answer = answerRepository.getOne(id);
            answer.isSameWriter(loginUser);
            question.deleteAnswerCount();
            answerRepository.deleteById(id);

            return String.format("redirect:/questions/%d/questionShow",questionId);

        }catch (IllegalStateException e){ // 로그인해야합니다.
            model.addAttribute("errorMessage",e.getMessage());
            return "user/login";
        }catch (NotMatchIdException e) {
            Question question = questionRepository.getOne(id); // 에러메시지를 리스트에 뿌리기 위해 다시 값을 가져옴
            model.addAttribute("question",question);
            model.addAttribute("errorMessage",e.getMessage());
            return "qna/qnaShow";
        }
    }
}
