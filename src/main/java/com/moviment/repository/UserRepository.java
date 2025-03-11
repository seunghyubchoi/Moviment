package com.moviment.repository;

import com.moviment.dto.UserInfoDTO;
import com.moviment.model.UserVO;

import javax.validation.Valid;

public interface UserRepository {
    void saveUser(UserVO user);
    UserVO getUser(UserVO user);
    UserVO findUserByEmail(String userEmail);
    void updatePwdErrCnt(int errCnt, String userEmail);

    void updateUserInfo(@Valid UserInfoDTO user);
}
