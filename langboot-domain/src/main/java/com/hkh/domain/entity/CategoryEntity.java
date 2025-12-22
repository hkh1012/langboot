package com.hkh.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 类目 实体类
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021/08/05 21:26:58
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
@TableName("category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目 类型
     *
     */
    private Integer categoryType;

    /**
     * 父级类目id
     */
    private Long parentId;

    /**
     * 是否禁用
     */
    private Boolean disabledFlag;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 删除状态
     */
    private Boolean deletedFlag;

    /**
     * 备注
     */
    private String remark;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}
