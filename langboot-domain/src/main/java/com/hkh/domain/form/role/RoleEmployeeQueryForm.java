package com.hkh.domain.form.role;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色的员工查询
 *
 * @Author 1024创新实验室: 善逸
 * @Date 2022-04-08 21:53:04
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class RoleEmployeeQueryForm extends PageParam {

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "角色id")
    private String roleId;
}
