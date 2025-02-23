package com.moviment.repository;

import com.moviment.model.UserVO;

public interface UserRepository {
    void saveUser(UserVO user);
    UserVO getUser(UserVO user);
    UserVO findUserByEmail(String userEmail);
    int updatePwdErrCnt(int errCnt, String userEmail);
}
