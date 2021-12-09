package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.data.JwtTokenDto;
import com.nixsolutions.crudapp.data.PublicUserDto;
import com.nixsolutions.crudapp.data.UserDtoForCreate;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.exception.FormProcessingException;
import com.nixsolutions.crudapp.jwt.JwtTokenProvider;
import com.nixsolutions.crudapp.mapper.UserMapper;
import com.nixsolutions.crudapp.service.UserService;
import com.nixsolutions.crudapp.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserValidator validationService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public Map<String, String> create(User user) {

        Map<String, String> invalidFields = new HashMap<>();
        try {
            validationService.validateCreate(user);
        } catch (FormProcessingException e) {
            invalidFields.put(e.getAttributeName(), e.getMessage());
            return invalidFields;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
        return invalidFields;
    }

    @Override
    public Map<String, String> update(UserDtoForCreate userDtoForCreate) {
        Map<String, String> invalidFields = new HashMap<>();
        try {
            validationService.validateUpdate(userDtoForCreate);
        } catch (FormProcessingException e) {
            invalidFields.put(e.getAttributeName(), e.getMessage());
            return invalidFields;
        }
        userDao.update(
                userMapper.fromDtoForCreateToUserUpdate(userDtoForCreate));
        return invalidFields;
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Override
    public List<PublicUserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream().map(userMapper::publicUserDtoFromUser)
                .collect(Collectors.toList());
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public JwtTokenDto getToken(User user) {
        return new JwtTokenDto(user.getLogin(),
                tokenProvider.createToken(user.getLogin(),
                        user.getRole().getName()), user.getRole().getName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDao.findByLogin(username);
    }
}