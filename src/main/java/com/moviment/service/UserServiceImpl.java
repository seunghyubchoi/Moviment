package com.moviment.service;

import com.moviment.model.UserVO;
import com.moviment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(UserVO user) {
        userRepository.saveUser(user);
    }

    @Override
    public UserVO getUser(UserVO user) {
        return userRepository.getUser(user);
    }
}
