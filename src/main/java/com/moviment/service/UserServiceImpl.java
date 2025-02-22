package com.moviment.service;

import com.moviment.model.UserVO;
import com.moviment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserVO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
    }

    @Override
    public UserVO getUser(UserVO user, Model model, BindingResult result) {
        UserVO savedUser = findByUserEmail(user.getEmail());

        String loginPassword = user.getPassword();
        String savedPassword = savedUser.getPassword();

        if (!checkPassword(loginPassword, savedPassword)) {
            model.addAttribute("errorMessage", result.getAllErrors().get(0).getDefaultMessage());
            return null;
        } else {
            return userRepository.getUser(user);
        }
    }

    @Override
    public boolean checkPassword(String loginPassword, String savedPassword) {
        return passwordEncoder.matches(loginPassword, savedPassword);
    }

    @Override
    public UserVO findByUserEmail(String userEmail) {
        UserVO savedUser = userRepository.findUserByEmail(userEmail);
        if(savedUser == null) {
            System.out.println("등록된 사용자가 없습니다.");
        } else {
            return savedUser;
        }
        return null;
    }


}
