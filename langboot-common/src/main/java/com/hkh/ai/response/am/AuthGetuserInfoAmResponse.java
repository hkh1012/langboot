package com.hkh.ai.response.am;

import lombok.Data;

import java.util.List;

@Data
public class AuthGetuserInfoAmResponse {
    private List<RoleInfo> roles;
    // 用户id
    private String userId;
    // 用户名
    private String username;
    // 真实名字
    private String realName;
    // 头像
    private String avatar;
    // 介绍
    private String desc;

    @Data
    class RoleInfo{
        private String roleName;
        // 用户名
        private String string;
    }
}
