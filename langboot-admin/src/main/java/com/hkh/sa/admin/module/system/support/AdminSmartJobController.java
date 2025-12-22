package com.hkh.sa.admin.module.system.support;

import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.RequestUser;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.form.job.*;
import com.hkh.domain.vo.job.SmartJobLogVO;
import com.hkh.domain.constant.SwaggerTagConst;
import com.hkh.sa.base.module.support.job.vo.SmartJobVO;
import com.hkh.sa.base.common.controller.SupportBaseController;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import com.hkh.sa.base.module.support.job.api.SmartJobService;
import com.hkh.sa.base.module.support.job.config.SmartJobAutoConfiguration;
import com.hkh.sa.base.module.support.repeatsubmit.annoation.RepeatSubmit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务 管理接口
 *
 * @author huke
 * @date 2024/6/17 20:41
 */
@Tag(name = SwaggerTagConst.Support.JOB)
@RestController
@ConditionalOnBean(SmartJobAutoConfiguration.class)
public class AdminSmartJobController extends SupportBaseController {

    @Autowired
    private SmartJobService jobService;

    @Operation(summary = "定时任务-立即执行")
    @PostMapping("/job/execute")
    @RepeatSubmit
    public ResponseDTO<String> execute(@RequestBody @Valid SmartJobExecuteForm executeForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        executeForm.setUpdateName(requestUser.getUserName());
        return jobService.execute(executeForm);
    }

    @Operation(summary = "定时任务-查询详情")
    @GetMapping("/job/{jobId}")
    public ResponseDTO<SmartJobVO> queryJobInfo(@PathVariable("jobId") Integer jobId) {
        return jobService.queryJobInfo(jobId);
    }

    @Operation(summary = "定时任务-分页查询")
    @PostMapping("/job/query")
    public ResponseDTO<PageResult<SmartJobVO>> queryJob(@RequestBody @Valid SmartJobQueryForm queryForm) {
        return jobService.queryJob(queryForm);
    }

    @Operation(summary = "定时任务-添加任务")
    @PostMapping("/job/add")
    @RepeatSubmit
    public ResponseDTO<String> addJob(@RequestBody @Valid SmartJobAddForm addForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        addForm.setUpdateName(requestUser.getUserName());
        return jobService.addJob(addForm);
    }

    @Operation(summary = "定时任务-更新-任务信息")
    @PostMapping("/job/update")
    @RepeatSubmit
    public ResponseDTO<String> updateJob(@RequestBody @Valid SmartJobUpdateForm updateForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        updateForm.setUpdateName(requestUser.getUserName());
        return jobService.updateJob(updateForm);
    }

    @Operation(summary = "定时任务-更新-开启状态")
    @PostMapping("/job/update/enabled")
    @RepeatSubmit
    public ResponseDTO<String> updateJobEnabled(@RequestBody @Valid SmartJobEnabledUpdateForm updateForm) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        updateForm.setUpdateName(requestUser.getUserName());
        return jobService.updateJobEnabled(updateForm);
    }

    @Operation(summary = "定时任务-删除 @zhuoda")
    @GetMapping("/job/delete")
    @RepeatSubmit
    public ResponseDTO<String> deleteJob(@RequestParam("jobId") Integer jobId) {
        return jobService.deleteJob(jobId, SmartRequestUtil.getRequestUser());
    }

    @Operation(summary = "定时任务-执行记录-分页查询")
    @PostMapping("/job/log/query")
    public ResponseDTO<PageResult<SmartJobLogVO>> queryJobLog(@RequestBody @Valid SmartJobLogQueryForm queryForm) {
        return jobService.queryJobLog(queryForm);
    }
}