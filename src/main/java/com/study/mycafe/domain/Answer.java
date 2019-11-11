package com.study.mycafe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_idx")
    @ToString.Exclude
    private Question question;

    private String contents;

    private LocalDateTime createdAt;


    public static Answer createAnswer(User user, Question question, String contents) {

        Answer answer = new Answer();
        answer.setUser(user);
        answer.setQuestion(question);
        answer.setContents(contents);
        answer.setCreatedAt(LocalDateTime.now());
        return answer;

    }


    public String getFormattedCreatedAt() {
        if(createdAt == null) {
            return "";
        }
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss"));
    }

}
