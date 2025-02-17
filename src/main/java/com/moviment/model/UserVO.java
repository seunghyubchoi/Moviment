package com.moviment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserVO {
    private String id;
    private String password;
    private String email;
    private String username;
    private String auth_provider;
    private String role;
}
