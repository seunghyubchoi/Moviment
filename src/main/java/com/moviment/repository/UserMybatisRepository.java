package com.moviment.repository;

import com.moviment.mapper.UserMapper;
import com.moviment.model.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserMybatisRepository implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(UserVO user) {
        userMapper.saveUser(user);
    }

    @Override
    public UserVO getUser(UserVO user) {
        return userMapper.getUser(user);
    }
}
