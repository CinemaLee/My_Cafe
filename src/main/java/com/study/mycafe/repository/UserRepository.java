package com.study.mycafe.repository;


import com.study.mycafe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);


//    List<User> findAllByUserId(String term); findAllBy랑 findBy랑 같이 인식함.
}
