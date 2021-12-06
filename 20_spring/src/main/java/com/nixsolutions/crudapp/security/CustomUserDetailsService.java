package com.nixsolutions.crudapp.security;

import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "Check the entered login or password!");
        }
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(
                login);
        userBuilder.password(user.getPassword());
        userBuilder.roles(user.getRole().getName());
        return userBuilder.build();
    }
}