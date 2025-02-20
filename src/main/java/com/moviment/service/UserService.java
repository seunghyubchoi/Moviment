package com.moviment.service;

import com.moviment.model.UserVO;

public interface UserService {
    void saveUser(UserVO user);
    UserVO getUser(UserVO user);
    boolean checkPassword(String loginPassword, String savedPassword);
    UserVO findByUserEmail(String userEmail);
}
