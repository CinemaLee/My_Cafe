package com.study.mycafe.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;

    @Lob // 대용량 글자수 가능.
    private String contents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx")
    @ToString.Exclude
    private User user;


    @OneToMany(mappedBy = "question")
    @ToString.Exclude
    @OrderBy("id ASC") // id기준 오름차순 정렬하겠다.
    private List<Answer> answers = new ArrayList<>();


    public static Question createQuestion(User user,String title, String contents) {

        Question question = new Question();
        question.setUser(user);
        question.setTitle(title);
        question.setContents(contents);
        question.setCreatedAt(LocalDateTime.now());


        return question;

    }


    public String getFormattedCreatedAt() {
        if(createdAt == null) {
            return "";
        }
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss"));
    }


    public boolean isSameUser(User loginUser) {
        System.out.println("user: "+user);
        System.out.println(this.user.equals(loginUser)); // 선생님 왜 false가 나오죠
        return this.user.equals(loginUser); // 내용이 같으면 참이도록 내가 재정의 한것. @Data안에 포함.
    }
}
