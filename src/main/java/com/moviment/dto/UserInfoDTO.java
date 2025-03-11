package com.moviment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 8, message = "이름은 최소 2자 이상, 최대 8자까지 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 10, message = "비밀번호는 최소 6자 이상, 최대 10자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$", message = "최소 1개의 대문자 및 1개의 특수문자(@$!%*?&)를 포함해야 합니다.")
    private String newPassword;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 10, message = "비밀번호는 최소 6자 이상, 최대 10자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$", message = "최소 1개의 대문자 및 1개의 특수문자(@$!%*?&)를 포함해야 합니다.")
    private String confirmPassword;
}
