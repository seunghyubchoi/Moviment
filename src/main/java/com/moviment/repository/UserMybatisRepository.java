package com.moviment.repository;

import com.moviment.mapper.UserMapper;
import com.moviment.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMybatisRepository implements UserRepository {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void saveUser(UserVO user) {
        userMapper.saveUser(user);
    }

    @Override
    public UserVO getUser(UserVO user) {
        return userMapper.getUser(user);
    }

    @Override
    public UserVO findUserByEmail(String userEmail) {
        return userMapper.findUserByEmail(userEmail);
    }

    @Override
    public void updatePwdErrCnt(int errCnt, String userEmail) {
        userMapper.updatePwdErrCnt(errCnt, userEmail);
    }
}
