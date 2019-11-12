package com.study.mycafe.service;

import com.study.mycafe.domain.User;
import com.study.mycafe.exception.PersonNotFoundException;
import com.study.mycafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void loginLogic(User user, String password) {

        if(user == null) {
            throw new IllegalStateException("존재하지 않는 아이디 입니다.");
        }

        user.matchPassword(password);

    }


    public void updateUser(Long id, String userId, String name, String email, String password) {
        User userAtDb = userRepository.findById(id).orElseThrow(PersonNotFoundException::new); // 영속상태. 값수정하고 따로 persist필요없다. 트랜잭션이 끝나면서 알아서 업데이트 쿼리가 나감.
        userAtDb.setUserId(userId);
        userAtDb.setName(name);
        userAtDb.setEmail(email);
        userAtDb.setPassword(password); // 변경감지로 트랜잭션이 끝나는 시점에 저장이 됨.

    }



}
