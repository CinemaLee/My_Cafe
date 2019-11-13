package com.study.mycafe.web;

import com.study.mycafe.domain.Answer;
import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import com.study.mycafe.exception.NotMatchIdException;
import com.study.mycafe.exception.QuestionNotFoundException;
import com.study.mycafe.repository.AnswerRepository;
import com.study.mycafe.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * api Controller로 만들어보자.
 */

@RestController // 반환값을 자동으로 json형태로 변환해줌.
@RequestMapping("/api/questions/{questionId}/answers")
public class AnswerApiController {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Header create(@PathVariable Long questionId, String contents, HttpSession session) {
        try {
            SessionUtils.isLoginUser(session);
            User loginUser = SessionUtils.getUserFromSession(session);
            Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
            Answer answer = Answer.createAnswer(loginUser, question, contents);
            question.addAnswerCount();
            return Header.Ok(answerRepository.save(answer)); // save는 그 엔티티 자체를 반환한다. 원래 이러면 안되고 DTO를 만들어야해.

        }catch (IllegalStateException e){ // 로그인해야합니다.

            return Header.Error("로그인 해야합니다.");
        }
    }


    @DeleteMapping("/{id}")
    public Header delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        try {
            SessionUtils.isLoginUser(session);
            User loginUser = SessionUtils.getUserFromSession(session);
            Question question = questionRepository.getOne(questionId);
            Answer answer = answerRepository.getOne(id);
            answer.isSameWriter(loginUser);
            question.deleteAnswerCount();
            answerRepository.deleteById(id);


            return Header.Ok("삭제되었습니다.");

        }catch (IllegalStateException e){ // 로그인해야합니다.
            return Header.Error("로그인 해야합니다.");
        }catch (NotMatchIdException e) {
            return Header.Error("해당 유저가 아닙니다.");
        }
    }
}
