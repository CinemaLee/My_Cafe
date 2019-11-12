package com.study.mycafe.web;

import com.study.mycafe.domain.User;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    static final String USER_SESSION_KEY = "loginUser"; // 변하지않는 상수값.


    static boolean isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);
        if(sessionUser == null) {
            throw new IllegalStateException("로그인을 해야합니다.");
        }
        return true; //null이 아니면(세션이 있으면) 트루를 리턴.
    }


    static User getUserFromSession(HttpSession session) {
        if(!isLoginUser(session)){ // 세션이 없다면 비로그인 이라면.
            return null;
        }
        return (User)session.getAttribute(USER_SESSION_KEY); // 로그인 되있으면
    }

}
