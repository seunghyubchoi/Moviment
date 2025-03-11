package com.moviment.service;

import com.moviment.dto.UserInfoDTO;
import com.moviment.model.UserVO;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

public interface UserService {
    void saveUser(UserVO user);
    UserVO getUser(UserVO user, Model model, BindingResult result);
    boolean checkPassword(String loginPassword, String savedPassword);
    UserVO findByUserEmail(String userEmail);

    void updateUserInfo(@Valid UserInfoDTO user);
}
