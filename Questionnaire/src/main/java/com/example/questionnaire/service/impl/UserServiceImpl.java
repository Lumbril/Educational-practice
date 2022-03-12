package com.example.questionnaire.service.impl;

import com.example.questionnaire.entity.Role;
import com.example.questionnaire.entity.User;
import com.example.questionnaire.repository.RoleRepository;
import com.example.questionnaire.repository.UserRepository;
import com.example.questionnaire.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User addUser(User user) throws Exception {
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new Exception("this user already exists");
        }

        Role role = roleRepository.findByName("USER");

        user.setRoles(Collections.singleton(role != null ? role : roleRepository.save(new Role("USER"))));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return user;
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }
}
