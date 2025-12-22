package com.hkh.sa.admin.module.system.support;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.RequestUser;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import com.hkh.sa.base.module.support.operatelog.OperateLogService;
import com.hkh.domain.form.operatelog.OperateLogQueryForm;
import com.hkh.domain.vo.operatelog.OperateLogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 *  操作日志
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2021-12-08 20:48:52
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = SwaggerTagConst.Support.OPERATE_LOG)
public class AdminOperateLogController extends SupportBaseController {

    @Resource
    private OperateLogService operateLogService;

    @Operation(summary = "分页查询")
    @PostMapping("/operateLog/page/query")
    @SaCheckPermission("support:operateLog:query")
    public ResponseDTO<PageResult<OperateLogVO>> queryByPage(@RequestBody OperateLogQueryForm queryForm) {
        return operateLogService.queryByPage(queryForm);
    }

    @Operation(summary = "详情")
    @GetMapping("/operateLog/detail/{operateLogId}")
    @SaCheckPermission("support:operateLog:detail")
    public ResponseDTO<OperateLogVO> detail(@PathVariable("operateLogId") Long operateLogId) {
        return operateLogService.detail(operateLogId);
    }

    @Operation(summary = "分页查询当前登录人信息")
    @PostMapping("/operateLog/page/query/login")
    public ResponseDTO<PageResult<OperateLogVO>> queryByPageLogin(@RequestBody OperateLogQueryForm queryForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        queryForm.setOperateUserId(requestUser.getUserId());
        queryForm.setOperateUserType(requestUser.getUserType().getValue());
        return operateLogService.queryByPage(queryForm);
    }

}
