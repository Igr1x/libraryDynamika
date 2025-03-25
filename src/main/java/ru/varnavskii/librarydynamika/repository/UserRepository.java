package ru.varnavskii.librarydynamika.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.varnavskii.librarydynamika.repository.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
