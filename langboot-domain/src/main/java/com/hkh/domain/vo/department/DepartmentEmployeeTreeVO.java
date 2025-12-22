package com.hkh.domain.vo.department;

import com.hkh.domain.vo.employee.EmployeeVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 部门
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-01-12 20:37:48
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class DepartmentEmployeeTreeVO extends DepartmentVO {

    @Schema(description = "部门员工列表")
    private List<EmployeeVO> employees;

    @Schema(description = "子部门")
    private List<DepartmentEmployeeTreeVO> children;

}
