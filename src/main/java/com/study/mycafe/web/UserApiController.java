package com.study.mycafe.web;

import com.study.mycafe.domain.User;
import com.study.mycafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public Header show(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(u -> Header.Ok(u))
                .orElseGet(()->Header.Error("사용자를 찾을 수 없습니다."));


    }
}
