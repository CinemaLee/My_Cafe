package com.study.mycafe.exception;


public class QuestionNotFoundException extends RuntimeException{

    private static final String message="존재하지 않는 질문입니다.";

    public QuestionNotFoundException() {
        super(message);
    }
}
