package com.hkh.domain.vo.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统访问记录表 sys_logininfor
 * </p>
 *
 * @author majb
 * @since 2024-08-12
 */
@Data
public class SysLogininforPageList implements Serializable {

    /** 用户账号 */
    private String username;

    /** 登录状态 1成功 0失败 */
    private String status;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 提示消息 */
    private String msg;

    /** 登录时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    private String name;
    private String userNo;
    private String nickName;
    private String deptName;
    private String deptNo;

}
