package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import ru.varnavskii.librarydynamika.repository.UserRepository;
import ru.varnavskii.librarydynamika.repository.entity.UserEntity;
import ru.varnavskii.librarydynamika.service.UserService;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserOrThrowException(long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("User with id '%d' not found", id)));
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
