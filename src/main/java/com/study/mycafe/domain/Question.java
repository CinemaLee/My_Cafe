package com.study.mycafe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.mycafe.exception.NotMatchIdException;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question extends SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Lob // 대용량 글자수 가능.
    private String contents;

    private Integer countOfAnswer=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;


    @OneToMany(mappedBy = "question")
    @OrderBy("id DESC") // id기준 내림차순 정렬하겠다. <=> ASC
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();


    public static Question createQuestion(User user,String title, String contents) {

        Question question = new Question();
        question.setUser(user);
        question.setTitle(title);
        question.setContents(contents);
        return question;

    }

    public void isSameUser(User loginUser) {
        if(!this.user.equals(loginUser)) {
            throw new NotMatchIdException();
        }
    }

    public void addAnswerCount() {
        this.countOfAnswer += 1;
    }

    public void deleteAnswerCount(){
        this.countOfAnswer -= 1;
    }
}
