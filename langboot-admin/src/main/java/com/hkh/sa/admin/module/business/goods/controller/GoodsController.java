package com.hkh.sa.admin.module.business.goods.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.common.ValidateList;
import com.hkh.sa.admin.constant.AdminSwaggerTagConst;
import com.hkh.sa.base.module.support.goods.form.GoodsAddForm;
import com.hkh.sa.base.module.support.goods.form.GoodsQueryForm;
import com.hkh.sa.base.module.support.goods.form.GoodsUpdateForm;
import com.hkh.domain.vo.goods.GoodsExcelVO;
import com.hkh.domain.vo.goods.GoodsVO;
import com.hkh.sa.admin.module.business.goods.service.GoodsService;
import com.hkh.sa.base.common.util.SmartExcelUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 商品业务
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_GOODS)
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Operation(summary = "分页查询")
    @PostMapping("/goods/query")
    @SaCheckPermission("goods:query")
    public ResponseDTO<PageResult<GoodsVO>> query(@RequestBody @Valid GoodsQueryForm queryForm) {
        return goodsService.query(queryForm);
    }

    @Operation(summary = "添加商品")
    @PostMapping("/goods/add")
    @SaCheckPermission("goods:add")
    public ResponseDTO<String> add(@RequestBody @Valid GoodsAddForm addForm) {
        return goodsService.add(addForm);
    }

    @Operation(summary = "更新商品")
    @PostMapping("/goods/update")
    @SaCheckPermission("goods:update")
    public ResponseDTO<String> update(@RequestBody @Valid GoodsUpdateForm updateForm) {
        return goodsService.update(updateForm);
    }

    @Operation(summary = "删除")
    @GetMapping("/goods/delete/{goodsId}")
    @SaCheckPermission("goods:delete")
    public ResponseDTO<String> delete(@PathVariable("goodsId") Long goodsId) {
        return goodsService.delete(goodsId);
    }

    @Operation(summary = "批量")
    @PostMapping("/goods/batchDelete")
    @SaCheckPermission("goods:batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody @Valid ValidateList<Long> idList) {
        return goodsService.batchDelete(idList);
    }

    // --------------- 导出和导入 -------------------

    @Operation(summary = "导入")
    @PostMapping("/goods/importGoods")
    @SaCheckPermission("goods:importGoods")
    public ResponseDTO<String> importGoods(@RequestParam("file") MultipartFile file) {
        return goodsService.importGoods(file);
    }

    @Operation(summary = "导出")
    @GetMapping("/goods/exportGoods")
    @SaCheckPermission("goods:exportGoods")
    public void exportGoods(HttpServletResponse response) throws IOException {
        List<GoodsExcelVO> goodsList = goodsService.getAllGoods();
        SmartExcelUtil.exportExcel(response,"商品列表.xlsx","商品",GoodsExcelVO.class, goodsList);
    }

}
