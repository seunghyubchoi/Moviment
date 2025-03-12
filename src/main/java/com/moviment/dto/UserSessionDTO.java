package com.moviment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionDTO {
    private String id;
    private String email;
    private String username;
    private String auth_provider;
    private String role;
}
