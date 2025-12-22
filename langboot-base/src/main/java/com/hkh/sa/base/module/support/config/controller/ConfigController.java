package com.hkh.sa.base.module.support.config.controller;

import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.vo.config.ConfigVO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.module.support.config.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-14 20:46:27
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = SwaggerTagConst.Support.CONFIG)
@RestController
public class ConfigController extends SupportBaseController {

    @Resource
    private ConfigService configService;

    @Operation(summary = "查询配置详情")
    @GetMapping("/config/queryByKey")
    public ResponseDTO<ConfigVO> queryByKey(@RequestParam("configKey") String configKey) {
        return ResponseDTO.ok(configService.getConfig(configKey));
    }

}
