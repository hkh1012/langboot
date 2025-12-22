package com.hkh.sa.admin.module.business.oa.bank.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.RequestUser;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.domain.form.bank.BankCreateForm;
import com.hkh.domain.form.bank.BankQueryForm;
import com.hkh.domain.form.bank.BankUpdateForm;
import com.hkh.domain.vo.bank.BankVO;
import com.hkh.sa.admin.module.business.oa.bank.service.BankService;
import com.hkh.sa.base.common.util.SmartRequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OA办公-OA银行信息
 *
 * @Author 1024创新实验室:善逸
 * @Date 2022/6/23 21:59:22
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.OA_BANK)
public class BankController {

    @Resource
    private BankService bankService;

    @Operation(summary = "分页查询银行信息")
    @PostMapping("/oa/bank/page/query")
    @SaCheckPermission("oa:bank:query")
    public ResponseDTO<PageResult<BankVO>> queryByPage(@RequestBody @Valid BankQueryForm queryForm) {
        return bankService.queryByPage(queryForm);
    }

    @Operation(summary = "根据企业ID查询银行信息列表")
    @GetMapping("/oa/bank/query/list/{enterpriseId}")
    @SaCheckPermission("oa:bank:query")
    public ResponseDTO<List<BankVO>> queryList(@PathVariable("enterpriseId") Long enterpriseId) {
        return bankService.queryList(enterpriseId);
    }

    @Operation(summary = "查询银行信息详情")
    @GetMapping("/oa/bank/get/{bankId}")
    @SaCheckPermission("oa:bank:query")
    public ResponseDTO<BankVO> getDetail(@PathVariable("bankId") Long bankId) {
        return bankService.getDetail(bankId);
    }

    @Operation(summary = "新建银行信息")
    @PostMapping("/oa/bank/create")
    @SaCheckPermission("oa:bank:add")
    public ResponseDTO<String> createBank(@RequestBody @Valid BankCreateForm createVO) {
        RequestUser requestUser = SmartRequestUtil.getRequestUser();
        createVO.setCreateUserId(requestUser.getUserId());
        createVO.setCreateUserName(requestUser.getUserName());
        return bankService.createBank(createVO);
    }

    @Operation(summary = "编辑银行信息")
    @PostMapping("/oa/bank/update")
    @SaCheckPermission("oa:bank:update")
    public ResponseDTO<String> updateBank(@RequestBody @Valid BankUpdateForm updateVO) {
        return bankService.updateBank(updateVO);
    }

    @Operation(summary = "删除银行信息")
    @GetMapping("/oa/bank/delete/{bankId}")
    @SaCheckPermission("oa:bank:delete")
    public ResponseDTO<String> deleteBank(@PathVariable("bankId") Long bankId) {
        return bankService.deleteBank(bankId);
    }
}
