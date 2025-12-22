package com.hkh.domain.form.employee;

import com.hkh.domain.enums.base.GenderEnum;
import com.hkh.domain.enums.base.CheckEnum;
import com.hkh.domain.constant.SmartVerificationPattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 更新员工个人中心信息
 *
 * @Author 1024创新实验室: 开云
 * @Date 2021-12-20 21:06:49
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class EmployeeUpdateCenterForm {

    @Schema(hidden = true)
    private Long employeeId;

    @Schema(description = "姓名")
    @NotNull(message = "姓名不能为空")
    @Length(max = 30, message = "姓名最多30字符")
    private String actualName;

    @CheckEnum(value = GenderEnum.class, message = "性别错误")
    private Integer gender;

    @Schema(description = "手机号")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = SmartVerificationPattern.PHONE_REGEXP, message = "手机号格式不正确")
    private String phone;

    @Schema(description = "邮箱账号")
    @NotNull(message = "邮箱账号不能为空")
    @Pattern(regexp = SmartVerificationPattern.EMAIL, message = "邮箱账号格式不正确")
    private String email;

    @Schema(description = "职务级别ID")
    private Long positionId;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "备注")
    @Length(max = 200, message = "备注最多200字符")
    private String remark;
}