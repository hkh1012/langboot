package com.hkh.sa.admin.module.system.login.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.vo.captcha.CaptchaVO;
import com.hkh.domain.constant.RequestHeaderConst;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.login.LoginForm;
import com.hkh.domain.vo.login.LoginResultVO;
import com.hkh.sa.admin.module.system.login.service.LoginService;
import com.hkh.sa.admin.util.AdminRequestUtil;
import com.hkh.domain.annotation.NoNeedLogin;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import com.hkh.sa.base.module.support.securityprotect.service.Level3ProtectConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 员工登录
 *
 * @Author 1024创新实验室-主任:卓大
 * @Date 2021-12-15 21:05:46
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.System.SYSTEM_LOGIN)
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private Level3ProtectConfigService level3ProtectConfigService;

    @NoNeedLogin
    @PostMapping("/login")
    @Operation(summary = "登录")
    public ResponseDTO<LoginResultVO> login(@Valid @RequestBody LoginForm loginForm, HttpServletRequest request) {
        String ip = JakartaServletUtil.getClientIP(request);
        String userAgent = JakartaServletUtil.getHeaderIgnoreCase(request, RequestHeaderConst.USER_AGENT);
        return loginService.login(loginForm, ip, userAgent);
    }

    @GetMapping("/login/getLoginInfo")
    @Operation(summary = "获取登录结果信息")
    public ResponseDTO<LoginResultVO> getLoginInfo() {
        String tokenValue = StpUtil.getTokenValue();
        LoginResultVO loginResult = loginService.getLoginResult(AdminRequestUtil.getRequestUser(), tokenValue);
        loginResult.setToken(tokenValue);
        return ResponseDTO.ok(loginResult);
    }

    @Operation(summary = "退出登陆")
    @GetMapping("/login/logout")
    public ResponseDTO<String> logout() {
        return loginService.logout(SmartRequestUtil.getRequestUser());
    }

    @Operation(summary = "获取验证码")
    @GetMapping("/login/getCaptcha")
    @NoNeedLogin
    public ResponseDTO<CaptchaVO> getCaptcha() {
        return loginService.getCaptcha();
    }

    @NoNeedLogin
    @GetMapping("/login/sendEmailCode/{loginName}")
    @Operation(summary = "获取邮箱登录验证码")
    public ResponseDTO<String> sendEmailCode(@PathVariable("loginName") String loginName) {
        return loginService.sendEmailCode(loginName);
    }


    @NoNeedLogin
    @GetMapping("/login/getTwoFactorLoginFlag")
    @Operation(summary = "获取双因子登录标识")
    public ResponseDTO<Boolean> getTwoFactorLoginFlag() {
        // 双因子登录
        boolean twoFactorLoginEnabled = level3ProtectConfigService.isTwoFactorLoginEnabled();
        return ResponseDTO.ok(twoFactorLoginEnabled);
    }
}
