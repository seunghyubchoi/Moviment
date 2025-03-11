package com.moviment.mapper;

import com.moviment.dto.UserInfoDTO;
import com.moviment.model.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.Valid;

@Mapper
public interface UserMapper {
    UserVO getUser(UserVO user);
    void saveUser(UserVO user);
    UserVO findUserByEmail(String userEmail);
    void updatePwdErrCnt(@Param("errCnt") int errCnt, @Param("userEmail") String userEmail);

    void updateUserInfo(@Param("user") @Valid UserInfoDTO user);
}
