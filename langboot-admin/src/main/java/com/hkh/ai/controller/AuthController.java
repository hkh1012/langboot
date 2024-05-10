package com.hkh.ai.controller;

import com.hkh.ai.common.ResultCodeEnum;
import com.hkh.ai.common.ResultData;
import com.hkh.ai.common.annotation.TokenIgnore;
import com.hkh.ai.component.TokenService;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.AuthLoginRequest;
import com.hkh.ai.response.am.AuthGetuserInfoAmResponse;
import com.hkh.ai.response.am.AuthLoginAmResponse;
import com.hkh.ai.service.SysUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 用户认证
 */
@RestController
@AllArgsConstructor
public class AuthController {

    private final SysUserService sysUserService;
    private final TokenService tokenService;

    @PostMapping(value = "/login")
    @TokenIgnore
    public ResultData<AuthLoginAmResponse> login(@RequestBody AuthLoginRequest request) {
        SysUser sysUser = sysUserService.getByUsernameAndPassowrd(request.getUsername(),request.getPassword());
        if (sysUser == null){
            return ResultData.fail(ResultCodeEnum.AUTH_FAIL,"认证失败");
        }else {
            String token = tokenService.createJWT(sysUser.getNickName(),sysUser.getUserName(),"USER",null,null);
            AuthLoginAmResponse authLoginAmResponse= new AuthLoginAmResponse();
            authLoginAmResponse.setSysUser(sysUser);
            authLoginAmResponse.setToken(token);
            return ResultData.success(authLoginAmResponse,"登录成功");
        }
    }

    @GetMapping(value = "/getUserInfo")
    public ResultData<AuthGetuserInfoAmResponse> getUserInfo(HttpServletRequest httpServletRequest, @RequestAttribute(value = "userid") String userid){
        AuthGetuserInfoAmResponse response = new AuthGetuserInfoAmResponse();
        response.setUserId("abc");
        response.setUsername("eeef");
        response.setRealName("黄开汉");
        response.setAvatar("");
        response.setDesc("工程师");
        response.setRoles(new ArrayList<>());
        return ResultData.success(response,"查询成功");
    }

}
