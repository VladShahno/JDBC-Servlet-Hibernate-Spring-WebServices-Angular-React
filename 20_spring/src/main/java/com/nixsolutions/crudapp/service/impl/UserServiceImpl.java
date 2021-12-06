package com.nixsolutions.crudapp.service.impl;

import com.nixsolutions.crudapp.dao.UserDao;
import com.nixsolutions.crudapp.data.UserDto;
import com.nixsolutions.crudapp.entity.Role;
import com.nixsolutions.crudapp.entity.User;
import com.nixsolutions.crudapp.service.RoleService;
import com.nixsolutions.crudapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService,
            PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.create(user);
    }

    @Override
    public void update(UserDto userDto) {

        Optional<User> optionalUser = Optional.ofNullable(
                userDao.findByLogin(userDto.getLogin()));
        if (optionalUser.isEmpty()) {
            throw new RuntimeException();
        }
        User user = optionalUser.get();
        user.setLogin(userDto.getLogin());

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
            user.setPasswordConfirm(userDto.getPasswordConfirm());
        }
        user.setEmail(userDto.getEmail());
        user.setBirthday(userDto.getBirthday());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        Optional<Role> optionalRole = Optional.ofNullable(
                roleService.findById(userDto.getRole()));

        if (optionalRole.isEmpty()) {
            throw new RuntimeException();
        }

        user.setRole(optionalRole.get());
        userDao.update(user);
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
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
    public User convert(UserDto dto) {
        System.out.println(dto);
        return new User(dto.getLogin(), dto.getPassword(),
                dto.getPasswordConfirm(), dto.getEmail(), dto.getFirstName(),
                dto.getLastName(), dto.getBirthday(),
                roleService.findById(dto.getRole()));
    }
}