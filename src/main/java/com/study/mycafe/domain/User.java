package com.study.mycafe.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false) // null이 될 수 없다.
    private String userId;

    private String password;
    private String name;
    private String email;

}
