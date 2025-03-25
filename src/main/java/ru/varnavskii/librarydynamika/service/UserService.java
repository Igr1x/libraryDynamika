package ru.varnavskii.librarydynamika.service;

import ru.varnavskii.librarydynamika.repository.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity user);

    UserEntity getUserOrThrowException(long id);

    UserEntity updateUser(UserEntity user);

    List<UserEntity> getAllUsers();

    void deleteUser(long id);
}
