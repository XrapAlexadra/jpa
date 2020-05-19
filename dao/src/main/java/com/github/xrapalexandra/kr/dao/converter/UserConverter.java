package com.github.xrapalexandra.kr.dao.converter;

import com.github.xrapalexandra.kr.dao.entity.UserEntity;
import com.github.xrapalexandra.kr.model.User;

public class UserConverter {

    public static UserEntity toEntity(User user) {
        if (user == null)
            return null;
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setLogin(user.getLogin());
        userEntity.setPass(user.getPass());
        userEntity.setRole(user.getRole());
        return userEntity;
    }

    public static User fromEntity(UserEntity userEntity){
        if (userEntity == null)
            return null;
        final User user = new User(
                userEntity.getLogin(),
                userEntity.getRole(),
                userEntity.getPass()
        );
        user.setId(userEntity.getId());
        return user;
    }

}
