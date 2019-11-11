package com.study.mycafe.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // null이 될 수 없다. 똑같을 수 없다.
    private String userId;

    private String password;
    private String name;
    private String email;


    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Question> questions = new ArrayList<>();



    public boolean matchPassword(String newPassword) {
        if(newPassword == null) {
            return false;
        }
        return newPassword.equals(password);
    }

    public boolean matchId(Long newId) {
        if(newId == null) {
            return false;
        }
        return newId.equals(id);
    }

}
