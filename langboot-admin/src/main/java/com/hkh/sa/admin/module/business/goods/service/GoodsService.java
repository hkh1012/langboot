package com.hkh.sa.admin.module.business.goods.service;

import cn.idev.excel.FastExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.common.errorcode.UserErrorCode;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.enums.DataTracerTypeEnum;
import com.hkh.domain.enums.CategoryTypeEnum;
import com.hkh.domain.entity.CategoryEntity;
import com.hkh.sa.admin.module.business.category.service.CategoryQueryService;
import com.hkh.domain.enums.GoodsStatusEnum;
import com.hkh.sa.admin.module.business.goods.dao.GoodsDao;
import com.hkh.domain.entity.GoodsEntity;
import com.hkh.sa.base.module.support.goods.form.GoodsAddForm;
import com.hkh.sa.base.module.support.goods.form.GoodsImportForm;
import com.hkh.sa.base.module.support.goods.form.GoodsQueryForm;
import com.hkh.sa.base.module.support.goods.form.GoodsUpdateForm;
import com.hkh.domain.vo.goods.GoodsExcelVO;
import com.hkh.domain.vo.goods.GoodsVO;
import com.hkh.sa.base.common.exception.BusinessException;
import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.common.util.SmartEnumUtil;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.sa.base.module.support.datatracer.service.DataTracerService;
import com.hkh.sa.base.module.support.dict.service.DictService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品
 *
 * @Author 1024创新实验室: 胡克
 * @Date 2021-10-25 20:26:54
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
@Slf4j
public class GoodsService {

    @Resource
    private GoodsDao goodsDao;

    @Resource
    private CategoryQueryService categoryQueryService;

    @Resource
    private DataTracerService dataTracerService;

    @Resource
    private DictService dictService;

    /**
     * 添加商品
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(GoodsAddForm addForm) {
        // 商品校验
        ResponseDTO<String> res = this.checkGoods(addForm);
        if (!res.getOk()) {
            return res;
        }
        GoodsEntity goodsEntity = SmartBeanUtil.copy(addForm, GoodsEntity.class);
        goodsEntity.setDeletedFlag(Boolean.FALSE);
        goodsDao.insert(goodsEntity);
        dataTracerService.insert(goodsEntity.getGoodsId(), DataTracerTypeEnum.GOODS);
        return ResponseDTO.ok();
    }

    /**
     * 更新商品
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(GoodsUpdateForm updateForm) {
        // 商品校验
        ResponseDTO<String> res = this.checkGoods(updateForm);
        if (!res.getOk()) {
            return res;
        }
        GoodsEntity originEntity = goodsDao.selectById(updateForm.getGoodsId());
        GoodsEntity goodsEntity = SmartBeanUtil.copy(updateForm, GoodsEntity.class);
        goodsDao.updateById(goodsEntity);
        dataTracerService.update(updateForm.getGoodsId(), DataTracerTypeEnum.GOODS, originEntity, goodsEntity);
        return ResponseDTO.ok();
    }

    /**
     * 添加/更新 商品校验
     */
    private ResponseDTO<String> checkGoods(GoodsAddForm addForm) {
        // 校验类目id
        Long categoryId = addForm.getCategoryId();
        Optional<CategoryEntity> optional = categoryQueryService.queryCategory(categoryId);
        if (!optional.isPresent() || !CategoryTypeEnum.GOODS.equalsValue(optional.get().getCategoryType())) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST, "商品类目不存在~");
        }

        return ResponseDTO.ok();
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long goodsId) {
        GoodsEntity goodsEntity = goodsDao.selectById(goodsId);
        if (goodsEntity == null) {
            return ResponseDTO.userErrorParam("商品不存在");
        }

        if (!goodsEntity.getGoodsStatus().equals(GoodsStatusEnum.SELL_OUT.getValue())) {
            return ResponseDTO.userErrorParam("只有售罄的商品才可以删除");
        }

        batchDelete(Collections.singletonList(goodsId));
        dataTracerService.batchDelete(Collections.singletonList(goodsId), DataTracerTypeEnum.GOODS);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> goodsIdList) {
        if (CollectionUtils.isEmpty(goodsIdList)) {
            return ResponseDTO.ok();
        }

        goodsDao.batchUpdateDeleted(goodsIdList, Boolean.TRUE);
        return ResponseDTO.ok();
    }


    /**
     * 分页查询
     */
    public ResponseDTO<PageResult<GoodsVO>> query(GoodsQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<GoodsVO> list = goodsDao.query(page, queryForm);
        PageResult<GoodsVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        if (pageResult.getEmptyFlag()) {
            return ResponseDTO.ok(pageResult);
        }
        // 查询分类名称
        List<Long> categoryIdList = list.stream().map(GoodsVO::getCategoryId).distinct().collect(Collectors.toList());
        Map<Long, CategoryEntity> categoryMap = categoryQueryService.queryCategoryList(categoryIdList);
        list.forEach(e -> {
            CategoryEntity categoryEntity = categoryMap.get(e.getCategoryId());
            if (categoryEntity != null) {
                e.setCategoryName(categoryEntity.getCategoryName());
            }
        });
        return ResponseDTO.ok(pageResult);
    }

    /**
     * 商品导入
     *
     * @param file 上传文件
     * @return 结果
     */
    public ResponseDTO<String> importGoods(MultipartFile file) {
        List<GoodsImportForm> dataList;
        try {
            dataList = FastExcel.read(file.getInputStream()).head(GoodsImportForm.class)
                    .sheet()
                    .doReadSync();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("数据格式存在问题，无法读取");
        }

        if (CollectionUtils.isEmpty(dataList)) {
            return ResponseDTO.userErrorParam("数据为空");
        }

        return ResponseDTO.okMsg("成功导入" + dataList.size() + "条，具体数据为：" + JSON.toJSONString(dataList));
    }

    /**
     * 商品导出
     */
    public List<GoodsExcelVO> getAllGoods() {
        List<GoodsEntity> goodsEntityList = goodsDao.selectList(null);
        String dictCode = "GOODS_PLACE";
        return goodsEntityList.stream()
                .map(e ->
                        GoodsExcelVO.builder()
                                .goodsStatus(SmartEnumUtil.getEnumDescByValue(e.getGoodsStatus(), GoodsStatusEnum.class))
                                .categoryName(categoryQueryService.queryCategoryName(e.getCategoryId()))
                                .place(Arrays.stream(e.getPlace().split(",")).map(code -> dictService.getDictDataLabel(dictCode, code)).collect(Collectors.joining(",")))
                                .price(e.getPrice())
                                .goodsName(e.getGoodsName())
                                .remark(e.getRemark())
                                .build()
                )
                .collect(Collectors.toList());

    }
}
