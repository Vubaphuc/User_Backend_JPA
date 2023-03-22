package com.example.bean.reponsitory;

import com.example.bean.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(Integer id);
    Optional<User> findUserByName(String name);
}