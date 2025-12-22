package com.hkh.domain.vo.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(description = "部门树返回参数")
@Data
@AllArgsConstructor
public class SysDeptTreeResponse {

    @Schema(title = "list",description = "部门列表")
    private List<SysDeptTree> list;

}
