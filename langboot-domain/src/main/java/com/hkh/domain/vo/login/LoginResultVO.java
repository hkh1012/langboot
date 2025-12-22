package com.hkh.domain.vo.login;

import com.hkh.domain.common.RequestEmployee;
import com.hkh.domain.vo.menu.MenuVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录结果信息
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-19 11:49:45
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class LoginResultVO extends RequestEmployee {

    @Schema(description = "token")
    private String token;

    @Schema(description = "菜单列表")
    private List<MenuVO> menuList;

    @Schema(description = "是否需要修改密码")
    private Boolean needUpdatePwdFlag;

    @Schema(description = "上次登录ip")
    private String lastLoginIp;

    @Schema(description = "上次登录ip地区")
    private String lastLoginIpRegion;

    @Schema(description = "上次登录user-agent")
    private String lastLoginUserAgent;

    @Schema(description = "上次登录时间")
    private LocalDateTime lastLoginTime;

}
