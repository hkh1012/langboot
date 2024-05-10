package com.hkh.ai.response.am;

import com.hkh.ai.domain.SysUser;
import lombok.Data;

@Data
public class AuthLoginAmResponse {

    private SysUser sysUser;
    private String token;
}
