package com.hkh.domain.form.login;

import lombok.Data;

@Data
public class AuthLoginRequest {

    private String username;

    private String password;
}
