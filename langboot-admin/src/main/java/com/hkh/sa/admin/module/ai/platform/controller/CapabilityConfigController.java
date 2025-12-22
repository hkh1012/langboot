package com.hkh.sa.admin.module.ai.platform.controller;

import com.hkh.sa.base.module.support.capabilityconfig.service.CapabilityConfigService;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.entity.capabilityconfig.CapabilityConfigEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "模型能力配置")
@RestController
@AllArgsConstructor
@RequestMapping("capabilityConfig")
public class CapabilityConfigController {

    private final CapabilityConfigService capabilityConfigService;

    @GetMapping(value = "info")
    @Operation(summary = "配置信息", operationId = "capabilityConfigInfo")
    public ResponseDTO<CapabilityConfigEntity> capabilityConfigInfo() {
        CapabilityConfigEntity capabilityConfigEntity = capabilityConfigService.getConfig();
        return ResponseDTO.ok(capabilityConfigEntity);
    }

    @PostMapping(value = "save")
    @Operation(summary = "保存", operationId = "capabilityConfigSave")
    public ResponseDTO<String> save(@RequestBody CapabilityConfigEntity requestBody, @RequestAttribute(value = "userNo") String userNo) {
        capabilityConfigService.saveConfig(requestBody,userNo);
        return ResponseDTO.ok();
    }
}
