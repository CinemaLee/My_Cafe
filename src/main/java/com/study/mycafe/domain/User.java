package com.study.mycafe.domain;

import com.study.mycafe.exception.NotMatchIdException;
import com.study.mycafe.exception.NotMatchPasswordException;
import com.study.mycafe.exception.PersonNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Answer> answers = new ArrayList<>();



    public void matchPassword(String newPassword) {
        if(newPassword == null) {
            throw new NotMatchPasswordException();
        }
        if(!newPassword.equals(password)){
            throw new NotMatchPasswordException();
        }
    }

    public void matchId(Long newId) {
        if(newId == null) {
            throw new PersonNotFoundException();
        }
        if(!newId.equals(id)) {
            throw new NotMatchIdException();
        }
    }

    // Lombok의 @Data안에 있는 equals()로 하니 에러가 났다. 눈으로 보기에는 다 같은데 뭐가 문젠지.. 아마 sessionUser는 눈으로 보기와는 다르게 조금 다르게 생긴것같음.
    // 그래서 id만 같으면 같은값이라는 equals()를 따로 오버로딩 해서 사용.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
