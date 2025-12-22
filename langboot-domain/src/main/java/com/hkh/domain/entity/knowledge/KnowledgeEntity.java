package com.hkh.domain.entity.knowledge;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 知识库 实体类
 *
 * @Author huangkh
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
@TableName("knowledge")
public class KnowledgeEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 知识库编号
     */
    private String knowledgeNo;

    /**
     * 知识库名称
     */
    private String knowledgeName;

    /**
     * 封面图片地址
     */
    private String picUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：0停用，1在用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人工号
     */
    private String createBy;

    /**
     * 创建人姓名
     */
    private String createByName;

    /**
     * 最近更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人工号
     */
    private String updateBy;

    /**
     * 更新人姓名
     */
    private String updateByName;

}
