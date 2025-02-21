package com.moviment.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private String id;

    // 이메일, 비밀번호, 이름 순으로 입력 됨

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 10, message = "비밀번호는 최소 6자 이상, 최대 10자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$", message = "최소 1개의 대문자 및 1개의 특수문자(@$!%*?&)를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    private String auth_provider;

    private String role;
}
