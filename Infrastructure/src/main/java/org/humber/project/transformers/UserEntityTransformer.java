package org.humber.project.transformers;

import org.humber.project.domain.User;
import org.humber.project.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserEntityTransformer {
    public static User transformToUser(UserEntity userEntity) {
        User user = new User();
        user.setUserId(userEntity.getUserId());
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        // Add other fields as needed
        return user;
    }

    public static UserEntity transformToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        // Add other fields as needed
        return userEntity;
    }

    public static List<User> transformToUsers(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(UserEntityTransformer::transformToUser)
                .collect(Collectors.toList());
    }
}
