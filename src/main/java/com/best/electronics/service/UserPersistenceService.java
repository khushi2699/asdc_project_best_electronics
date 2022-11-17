package com.best.electronics.service;

import com.best.electronics.entity.User;

public interface UserPersistenceService {
    User findUserByEmailAddress(String emailAddress) throws Exception;
    User saveUser(User user);
}
