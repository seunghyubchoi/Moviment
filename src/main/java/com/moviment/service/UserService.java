package com.moviment.service;

import com.moviment.model.UserVO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface UserService {
    void saveUser(UserVO user);
    UserVO getUser(UserVO user, Model model, BindingResult result);
    boolean checkPassword(String loginPassword, String savedPassword);
    UserVO findByUserEmail(String userEmail);
}
