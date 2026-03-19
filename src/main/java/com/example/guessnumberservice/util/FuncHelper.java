package com.example.guessnumberservice.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class FuncHelper {
    public static String getUsernameByToken(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }
}
