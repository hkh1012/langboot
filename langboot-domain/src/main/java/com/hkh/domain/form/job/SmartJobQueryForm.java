package com.hkh.domain.form.job;

import com.hkh.domain.common.PageParam;
import com.hkh.domain.enums.SmartJobTriggerTypeEnum;
import com.hkh.domain.enums.base.CheckEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 定时任务 分页查询
 *
 * @author huke
 * @date 2024/6/17 20:50
 */
@Data
public class SmartJobQueryForm extends PageParam {

    @Schema(description = "搜索词|可选")
    @Length(max = 50, message = "搜索词最多50字符")
    private String searchWord;

    @CheckEnum(value = SmartJobTriggerTypeEnum.class, message = "触发类型错误")
    private String triggerType;

    @Schema(description = "是否启用|可选")
    private Boolean enabledFlag;

    @Schema(description = "是否删除|可选")
    private Boolean deletedFlag;
}
