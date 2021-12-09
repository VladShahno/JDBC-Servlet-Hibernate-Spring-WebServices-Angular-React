package com.nixsolutions.crudapp.service;

import com.nixsolutions.crudapp.data.JwtTokenDto;
import com.nixsolutions.crudapp.data.PublicUserDto;
import com.nixsolutions.crudapp.data.UserDtoForCreate;
import com.nixsolutions.crudapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {

    Map<String, String> create(User user);

    Map<String, String> update(UserDtoForCreate userDtoForCreate);

    void remove(User user);

    List<PublicUserDto> findAll();

    User findByLogin(String login);

    User findByEmail(String email);

    User findById(Long id);

    boolean existsByEmail(String email);

    JwtTokenDto getToken(User user);
}