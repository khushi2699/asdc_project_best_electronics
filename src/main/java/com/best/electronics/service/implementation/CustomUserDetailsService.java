package com.best.electronics.service.implementation;

import com.best.electronics.entity.User;
import com.best.electronics.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private UserRepository userRepo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepo.findUserByEmailAddress(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new CustomizedUserDetails(user);
        }
}
