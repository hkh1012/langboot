package com.hkh.sa.admin.module.ai.accesstoken.controller;

import com.hkh.domain.form.accesstoken.AccessTokenQueryForm;
import com.hkh.domain.vo.accesstoken.AccessTokenVO;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.base.module.support.accesstoken.service.AccessTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 第三方接口访问token Controller
 *
 *
 * @Date 2025-06-27 09:39:20
 * @Copyright https://github.com/hkh1012/langboot
 */

@RestController
@Tag(name = "第三方接口访问token")
@RequestMapping("accessToken")
public class AccessTokenController {

    @Resource
    private AccessTokenService accessTokenService;

    @Operation(summary = "分页查询")
    @PostMapping("/queryPage")
    @SaCheckPermission("accessToken:query")
    public ResponseDTO<PageResult<AccessTokenVO>> queryPage(@RequestBody @Valid AccessTokenQueryForm queryForm) {
        return ResponseDTO.ok(accessTokenService.queryPage(queryForm));
    }


}
