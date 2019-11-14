package com.study.mycafe.domain;

import com.study.mycafe.exception.NotMatchIdException;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Answer extends SuperEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_idx")
    private Question question;

    private String contents;

    public static Answer createAnswer(User user, Question question, String contents) {

        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setContents(contents);

        return answer;

    }

    public void isSameWriter(User loginUser) {
        if(!this.user.equals(loginUser)){
            throw new NotMatchIdException();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


}
