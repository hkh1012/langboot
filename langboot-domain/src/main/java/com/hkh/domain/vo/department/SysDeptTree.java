package com.hkh.domain.vo.department;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysDeptTree implements Serializable {

    private String id;

    private String parentId;

    /**
     * 部门编号
     */
    private String deptNo;

    /**
     * 上级部门编号
     */
    private String parentNo;

    /**
     * 上级部门编号
     */
    private String parentName;

    /**
     * 祖籍列表
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子部门
     */
    private List<SysDeptTree> children;

}

