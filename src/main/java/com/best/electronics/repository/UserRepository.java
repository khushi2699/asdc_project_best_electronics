package com.best.electronics.repository;

import com.best.electronics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmailAddress(String emailAddress);
}
