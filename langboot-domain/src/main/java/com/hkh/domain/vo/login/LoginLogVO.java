package com.hkh.domain.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022/07/22 19:46:23
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class LoginLogVO {

    private Long loginLogId;

    @Schema(description = "用户id")
    private Long userId;

    private Integer userType;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "登录ip")
    private String loginIp;

    @Schema(description = "登录ip地区")
    private String loginIpRegion;

    @Schema(description = "user-agent")
    private String userAgent;

    @Schema(description = "remark")
    private String remark;

    private Integer loginResult;

    private String loginDevice;

    private LocalDateTime createTime;

}
