package com.study.mycafe.exception;


public class PersonNotFoundException extends RuntimeException{

    private static final String message="회원이 존재하지 않습니다.";

    public PersonNotFoundException() {
        super(message);
    }
}
