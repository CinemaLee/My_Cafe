package com.study.mycafe.exception;


public class NotMatchIdException extends RuntimeException{

    private static final String message="해당 유저가 아닙니다.";

    public NotMatchIdException() {
        super(message);
    }
}
