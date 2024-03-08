package org.humber.project.services.impl;

import org.humber.project.domain.User;
import org.humber.project.entities.UserEntity;
import org.humber.project.repositories.UserJPARepository;
import org.humber.project.services.UserJPAService;
import org.humber.project.transformers.UserEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class UserJPAServiceImpl implements UserJPAService {
    private final UserJPARepository userJPARepository;

    @Autowired
    public UserJPAServiceImpl(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = UserEntityTransformer.transformToUserEntity(user);
        UserEntity savedEntity = userJPARepository.save(userEntity);
        return UserEntityTransformer.transformToUser(savedEntity);
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userJPARepository.findAll();
        return UserEntityTransformer.transformToUsers(userEntities);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<UserEntity> userEntityOptional = userJPARepository.findById(userId);
        return userEntityOptional.map(UserEntityTransformer::transformToUser).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<UserEntity> userEntityOptional = userJPARepository.findByUsername(username);
        return userEntityOptional.map(UserEntityTransformer::transformToUser).orElse(null);
    }

    public List<User> searchByUsername(String username) {
        List<UserEntity> userEntities = userJPARepository.findByUsernameContaining(username);
        return UserEntityTransformer.transformToUsers(userEntities);
    }
}
