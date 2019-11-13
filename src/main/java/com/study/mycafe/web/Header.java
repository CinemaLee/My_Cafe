package com.study.mycafe.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    private LocalDateTime transactionTime;
    private boolean resultCode;
    private String description;
    private T responseData;




    public static <T> Header<T> Ok(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(true)
                .description(description)
                .build();
    }

    public static <T> Header<T> Ok(T data) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(true)
                .responseData(data)
                .build();
    }

    public static <T> Header<T> Error(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode(false)
                .description(description)
                .build();
    }
}
