package com.moviment.service;

import com.moviment.model.UserVO;
import com.moviment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        System.out.println("BCrypt before saving");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        //userRepository.saveUser(user);
    }

    @Override
    public UserVO getUser(UserVO user) {

        // 입력 메일로 조회
        UserVO savedUser = findByUserEmail(user.getEmail());

        String loginPassword = user.getPassword();
        String savedPassword = savedUser.getPassword();

        if (!checkPassword(loginPassword, savedPassword)) {
            System.out.println("FAILLLLLLLLLLLLLLLLLLLL");
        } else {
            System.out.println("SUCCESSSSSSSSSSSSSSSSSS");
        }
        //return userRepository.getUser(user);
        return null;
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
