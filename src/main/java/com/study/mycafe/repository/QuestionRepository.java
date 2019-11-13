package com.study.mycafe.repository;


import com.study.mycafe.domain.Question;
import com.study.mycafe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByUser(User user); // 한개가 나올수도 여러개가 나올수도. 여러개 나올거 같다 싶으면 list로.

}
