package com.hkh.sa.admin.module.business.oa.notice.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.form.notice.NoticeEmployeeQueryForm;
import com.hkh.domain.form.notice.NoticeQueryForm;
import com.hkh.sa.admin.module.business.oa.notice.domain.form.NoticeUpdateForm;
import com.hkh.domain.form.notice.NoticeViewRecordQueryForm;
import com.hkh.domain.vo.notice.NoticeEmployeeVO;
import com.hkh.domain.vo.notice.NoticeTypeVO;
import com.hkh.domain.vo.notice.NoticeVO;
import com.hkh.domain.vo.notice.NoticeViewRecordVO;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.sa.admin.module.business.oa.notice.domain.form.*;
import com.hkh.sa.admin.module.business.oa.notice.domain.vo.*;
import com.hkh.sa.admin.module.business.oa.notice.service.NoticeEmployeeService;
import com.hkh.sa.admin.module.business.oa.notice.service.NoticeService;
import com.hkh.sa.admin.module.business.oa.notice.service.NoticeTypeService;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import com.hkh.domain.annotation.OperateLog;
import com.hkh.sa.base.module.support.repeatsubmit.annoation.RepeatSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告、通知、新闻等等
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 21:40:39
 * @Wechat 卓大1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Tag(name = AdminSwaggerTagConst.Business.OA_NOTICE)
@RestController
@OperateLog
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @Resource
    private NoticeTypeService noticeTypeService;

    @Resource
    private NoticeEmployeeService noticeEmployeeService;

    // --------------------- 通知公告类型 -------------------------

    @Operation(summary = "通知公告类型-获取全部")
    @GetMapping("/oa/noticeType/getAll")
    public ResponseDTO<List<NoticeTypeVO>> getAll() {
        return ResponseDTO.ok(noticeTypeService.getAll());
    }

    @Operation(summary = "通知公告类型-添加")
    @GetMapping("/oa/noticeType/add/{name}")
    public ResponseDTO<String> add(@PathVariable("name") String name) {
        return noticeTypeService.add(name);
    }

    @Operation(summary = "通知公告类型-修改")
    @GetMapping("/oa/noticeType/update/{noticeTypeId}/{name}")
    public ResponseDTO<String> update(@PathVariable("noticeTypeId") Long noticeTypeId, @PathVariable("name") String name) {
        return noticeTypeService.update(noticeTypeId, name);
    }

    @Operation(summary = "通知公告类型-删除")
    @GetMapping("/oa/noticeType/delete/{noticeTypeId}")
    public ResponseDTO<String> deleteNoticeType(@PathVariable("noticeTypeId") Long noticeTypeId) {
        return noticeTypeService.delete(noticeTypeId);
    }

    // --------------------- 【管理】通知公告-------------------------


    @Operation(summary = "【管理】通知公告-分页查询")
    @PostMapping("/oa/notice/query")
    @SaCheckPermission("oa:notice:query")
    public ResponseDTO<PageResult<NoticeVO>> query(@RequestBody @Valid NoticeQueryForm queryForm) {
        return ResponseDTO.ok(noticeService.query(queryForm));
    }

    @Operation(summary = "【管理】通知公告-添加")
    @PostMapping("/oa/notice/add")
    @RepeatSubmit
    @SaCheckPermission("oa:notice:add")
    public ResponseDTO<String> add(@RequestBody @Valid NoticeAddForm addForm) {
        addForm.setCreateUserId(SmartRequestUtil.getRequestUserId());
        return noticeService.add(addForm);
    }

    @Operation(summary = "【管理】通知公告-更新")
    @PostMapping("/oa/notice/update")
    @RepeatSubmit
    @SaCheckPermission("oa:notice:update")
    public ResponseDTO<String> update(@RequestBody @Valid NoticeUpdateForm updateForm) {
        return noticeService.update(updateForm);
    }

    @Operation(summary = "【管理】通知公告-更新详情")
    @GetMapping("/oa/notice/getUpdateVO/{noticeId}")
    @SaCheckPermission("oa:notice:update")
    public ResponseDTO<NoticeUpdateFormVO> getUpdateFormVO(@PathVariable("noticeId") Long noticeId) {
        return ResponseDTO.ok(noticeService.getUpdateFormVO(noticeId));
    }

    @Operation(summary = "【管理】通知公告-删除")
    @GetMapping("/oa/notice/delete/{noticeId}")
    @SaCheckPermission("oa:notice:delete")
    public ResponseDTO<String> delete(@PathVariable("noticeId") Long noticeId) {
        return noticeService.delete(noticeId);
    }

    // --------------------- 【员工】查看 通知公告 -------------------------


    @Operation(summary = "【员工】通知公告-查看详情")
    @GetMapping("/oa/notice/employee/view/{noticeId}")
    public ResponseDTO<NoticeDetailVO> view(@PathVariable("noticeId") Long noticeId, HttpServletRequest request) {
        return noticeEmployeeService.view(
                SmartRequestUtil.getRequestUserId(),
                noticeId,
                JakartaServletUtil.getClientIP(request),
                request.getHeader("User-Agent")
        );
    }

    @Operation(summary = "【员工】通知公告-查询全部")
    @PostMapping("/oa/notice/employee/query")
    public ResponseDTO<PageResult<NoticeEmployeeVO>> queryEmployeeNotice(@RequestBody @Valid NoticeEmployeeQueryForm noticeEmployeeQueryForm) {
        return noticeEmployeeService.queryList(SmartRequestUtil.getRequestUserId(), noticeEmployeeQueryForm);
    }

    @Operation(summary = "【员工】通知公告-查询 查看记录")
    @PostMapping("/oa/notice/employee/queryViewRecord")
    public ResponseDTO<PageResult<NoticeViewRecordVO>> queryViewRecord(@RequestBody @Valid NoticeViewRecordQueryForm noticeViewRecordQueryForm) {
        return ResponseDTO.ok(noticeEmployeeService.queryViewRecord(noticeViewRecordQueryForm));
    }
}