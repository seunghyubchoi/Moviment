package com.moviment.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 10, message = "비밀번호는 최소 6자 이상, 최대 10자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$", message = "최소 1개의 대문자 및 1개의 특수문자(@$!%*?&)를 포함해야 합니다.")
    private String password;

    @Email(message = "올바른 이메일 형식을 입력하세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    private String auth_provider;

    private String role;
}
