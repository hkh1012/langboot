package com.hkh.domain.form.notice;

import com.hkh.domain.common.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 通知公告 阅读记录查询
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Data
public class NoticeViewRecordQueryForm extends PageParam {

    @Schema(description = "通知公告id")
    @NotNull(message = "通知公告id不能为空")
    private Long noticeId;

    @Schema(description = "部门id")
    private Long departmentId;

    @Schema(description = "关键字")
    private String keywords;


}
