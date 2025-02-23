package com.moviment.service;

import com.moviment.exception.LoginException;
import com.moviment.model.UserVO;
import com.moviment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 비밀번호 BCrypt 암호화
     * @param passwordEncoder
     */
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     * @param user
     */
    @Override
    public void saveUser(UserVO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
    }

    /**
     * 로그인
     * @param user
     * @param model
     * @param result
     * @return
     */
    @Override
    public UserVO getUser(UserVO user, Model model, BindingResult result) {
        // 이메일 검증
        UserVO savedUser = findByUserEmail(user.getEmail());

        if (savedUser == null) {
            // 아이디 혹은 비밀번호 중 어느 것이 맞는지 모르게 끔
            // 대신 이메일이 틀린 경우 비밀번호 틀린 횟수 DB UPDATE XXXX
            throw new LoginException("아이디 및 비밀번호를 확인해주세요.");
        }

        // 이메일이 DB에 있는 경우, 입력한 비밀번호와 DB 저장 비밀번호 확인
        String loginPassword = user.getPassword();
        String savedPassword = savedUser.getPassword();

        if(!checkPassword(loginPassword, savedPassword)) {
            int pwdErrCnt = savedUser.getPwdErrCnt();

            if(pwdErrCnt >= 5) {
                throw new LoginException("로그인 5회 실패로 로그인이 제한되었습니다. \n관리자에게 문의해주세요.");
            }

            userRepository.updatePwdErrCnt(pwdErrCnt + 1, user.getEmail());
            throw new LoginException("아이디 또는 비밀번호를 확인해주세요.");

        }

        return savedUser;
    }

    /**
     * 입력 비밀번호와 저장된 비밀번호 매칭 확인
     * @param loginPassword
     * @param savedPassword
     * @return
     */
    @Override
    public boolean checkPassword(String loginPassword, String savedPassword) {
        return passwordEncoder.matches(loginPassword, savedPassword);
    }

    /**
     * DB에 회원 이메일 존재 유무 확인
     * @param userEmail
     * @return
     */
    @Override
    public UserVO findByUserEmail(String userEmail) {
        UserVO savedUser = userRepository.findUserByEmail(userEmail);
        if(savedUser == null) {
            return null;
        } else {
            return savedUser;
        }
    }


}
