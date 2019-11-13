package com.study.mycafe.service;

import com.study.mycafe.domain.Question;
import com.study.mycafe.exception.QuestionNotFoundException;
import com.study.mycafe.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;



    public void update(Long id,String title, String contents) {
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.setTitle(title);
        question.setContents(contents);

    }
}
