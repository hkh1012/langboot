package com.hkh.sa.base.module.support.serialnumber.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.common.PageResult;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.sa.base.module.support.serialnumber.dao.SerialNumberRecordDao;
import com.hkh.domain.entity.serialnumber.SerialNumberRecordEntity;
import com.hkh.domain.form.serialnumber.SerialNumberRecordQueryForm;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 单据序列号 记录
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-03-25 21:46:07
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class SerialNumberRecordService {

    @Resource
    private SerialNumberRecordDao serialNumberRecordDao;

    public PageResult<SerialNumberRecordEntity> query(SerialNumberRecordQueryForm queryForm) {
        Page page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SerialNumberRecordEntity> recordList = serialNumberRecordDao.query(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, recordList);
    }
}
