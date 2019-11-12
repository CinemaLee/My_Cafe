package com.study.mycafe.exception;


public class NotMatchPasswordException extends RuntimeException{

    private static final String message="비밀번호가 다릅니다.";

    public NotMatchPasswordException() {
        super(message);
    }
}
