package com.hkh.domain.form.role;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色 查询
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2022-02-26 19:09:42
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class RoleQueryForm extends PageParam {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色id")
    private String roleId;
}
