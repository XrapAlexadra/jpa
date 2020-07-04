package com.github.xrapalexandra.kr.web.util;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public class WebUtil {

    public static Boolean isAuthentication(){
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  !(authentication == null || "anonymousUser".equals(authentication.getPrincipal()));
    }

    public static <T> ModelAndView fillInModel(Page<T> page){
        ModelAndView model= new ModelAndView();
        model.addObject("list", page.getContent());
        model.addObject("pageCount", page.getTotalPages());
        model.addObject("page", page.getPageable().getPageNumber()+1);
        return model;
    }
}
