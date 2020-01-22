package com.andersonmarques.youtubenotes.services;

import java.util.Optional;

import com.andersonmarques.youtubenotes.models.User;
import com.andersonmarques.youtubenotes.models.UserDetailsImp;
import com.andersonmarques.youtubenotes.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User insert(User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid user");
        }
        return new UserDetailsImp(user.get());
    }
}
