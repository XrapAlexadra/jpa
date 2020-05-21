package com.github.xrapalexandra.kr.service.util;

import com.github.xrapalexandra.kr.model.Rating;
import com.github.xrapalexandra.kr.model.User;

import java.util.List;

public class ServiceUtil {

    public static Boolean isUserExist(List<Rating> ratingList, User user) {
        if (ratingList != null)
            for (Rating i : ratingList) {
                if (i.getUser().getId().equals(user.getId()))
                    return true;
            }
        return false;
    }
}
