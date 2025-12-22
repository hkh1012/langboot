package com.hkh.domain.vo.knowledge;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 知识库 列表VO
 *
 * @Author huangkh
 * @Date 2025-07-09 14:26:46
 * @Copyright https://github.com/hkh1012/langboot
 */

@Data
public class KnowledgeVO {


    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "知识库编号")
    private String knowledgeNo;

    @Schema(description = "知识库名称")
    private String knowledgeName;

    @Schema(description = "封面图片地址")
    private String picUrl;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "状态：0停用，1在用")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人姓名")
    private String createByName;

    @Schema(description = "最近更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人姓名")
    private String updateByName;

}
