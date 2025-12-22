package com.hkh.domain.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "登录返回结果")
@AllArgsConstructor
public class LoginResponse {

    @Schema(title = "accessToken", description = "访问token")
    private String accessToken;

}
