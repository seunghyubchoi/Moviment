package com.moviment.mapper;

import com.moviment.model.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserVO getUser(UserVO user);
    void saveUser(UserVO user);
}
