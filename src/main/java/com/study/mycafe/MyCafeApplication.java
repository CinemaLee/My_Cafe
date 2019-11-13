package com.study.mycafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 설정추가
public class MyCafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCafeApplication.class, args);
    }

}
