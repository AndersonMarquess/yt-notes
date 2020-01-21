package com.andersonmarques.youtubenotes.services;

import com.andersonmarques.youtubenotes.models.User;
import com.andersonmarques.youtubenotes.models.UserDetailsImp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImp(new User(1, username, "password"));
    }
}
