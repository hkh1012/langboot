package com.hkh.sa.base.module.support.datatracer.controller;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.form.datatracer.DataTracerQueryForm;
import com.hkh.domain.vo.datatracer.DataTracerVO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.module.support.datatracer.service.DataTracerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据变动记录
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-07-23 19:38:52
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = SwaggerTagConst.Support.DATA_TRACER)
@RestController
public class DataTracerController extends SupportBaseController {

    @Resource
    private DataTracerService dataTracerService;

    @Operation(summary = "分页查询业务操作日志 -")
    @PostMapping("/dataTracer/query")
    public ResponseDTO<PageResult<DataTracerVO>> query(@Valid @RequestBody DataTracerQueryForm queryForm) {
        return dataTracerService.query(queryForm);
    }
}
