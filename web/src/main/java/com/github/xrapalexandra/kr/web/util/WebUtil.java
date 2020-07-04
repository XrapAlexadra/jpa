package com.github.xrapalexandra.kr.web.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class WebUtil {

    public static Boolean isAuthentication(){
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  !(authentication == null || "anonymousUser".equals(authentication.getPrincipal()));
    }
}
