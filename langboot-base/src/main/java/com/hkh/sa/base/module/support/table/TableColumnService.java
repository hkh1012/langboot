package com.hkh.sa.base.module.support.table;

import com.alibaba.fastjson2.JSONArray;
import com.hkh.domain.common.RequestUser;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.entity.TableColumnEntity;
import com.hkh.domain.form.table.TableColumnUpdateForm;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * 表格自定义列（前端用户自定义表格列，并保存到数据库里）
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-08-12 22:52:21
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class TableColumnService {

    @Resource
    private TableColumnDao tableColumnDao;

    /**
     * 获取 - 表格列
     *
     * @return
     */
    public String getTableColumns(RequestUser requestUser, Integer tableId) {
        TableColumnEntity tableColumnEntity = tableColumnDao.selectByUserIdAndTableId(requestUser.getUserId(), requestUser.getUserType().getValue(), tableId);
        return tableColumnEntity == null ? null : tableColumnEntity.getColumns();
    }

    /**
     * 更新表格列
     *
     * @return
     */
    public ResponseDTO<String> updateTableColumns(RequestUser requestUser, TableColumnUpdateForm updateForm) {
        if (CollectionUtils.isEmpty(updateForm.getColumnList())) {
            return ResponseDTO.ok();
        }
        Integer tableId = updateForm.getTableId();
        TableColumnEntity tableColumnEntity = tableColumnDao.selectByUserIdAndTableId(requestUser.getUserId(), requestUser.getUserType().getValue(), tableId);
        if (tableColumnEntity == null) {
            tableColumnEntity = new TableColumnEntity();
            tableColumnEntity.setTableId(tableId);
            tableColumnEntity.setUserId(requestUser.getUserId());
            tableColumnEntity.setUserType(requestUser.getUserType().getValue());

            tableColumnEntity.setColumns(JSONArray.toJSONString(updateForm.getColumnList()));
            tableColumnDao.insert(tableColumnEntity);
        } else {
            tableColumnEntity.setColumns(JSONArray.toJSONString(updateForm.getColumnList()));
            tableColumnDao.updateById(tableColumnEntity);
        }
        return ResponseDTO.ok();
    }

    /**
     * 删除表格列
     *
     * @return
     */
    public ResponseDTO<String> deleteTableColumn(RequestUser requestUser, Integer tableId) {
        tableColumnDao.deleteTableColumn(requestUser.getUserId(), requestUser.getUserType().getValue(), tableId);
        return ResponseDTO.ok();
    }
}
