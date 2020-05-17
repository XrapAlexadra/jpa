package com.github.xrapalexandra.kr.service;

import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.User;

import java.util.List;

public class Util {

    public static Boolean isUserExist(List<Rating> ratingList, User user){
        for(Rating i: ratingList){
            if(i.getUser().getId().equals(user.getId()))
                return true;
        }
        return false;
    }
}
