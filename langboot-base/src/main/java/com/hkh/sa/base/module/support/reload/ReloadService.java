package com.hkh.sa.base.module.support.reload;

import com.hkh.domain.common.errorcode.UserErrorCode;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.sa.base.module.support.reload.dao.ReloadItemDao;
import com.hkh.sa.base.module.support.reload.dao.ReloadResultDao;
import com.hkh.domain.form.reload.ReloadForm;
import com.hkh.domain.entity.reload.ReloadItemEntity;
import com.hkh.domain.vo.reload.ReloadItemVO;
import com.hkh.domain.vo.reload.ReloadResultVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * reload (内存热加载、钩子等)
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2015-03-02 19:11:52
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class ReloadService {

    @Resource
    private ReloadItemDao reloadItemDao;

    @Resource
    private ReloadResultDao reloadResultDao;

    /**
     * 查询
     *
     * @return
     */
    public ResponseDTO<List<ReloadItemVO>> query() {
        List<ReloadItemVO> list = reloadItemDao.query();
        return ResponseDTO.ok(list);
    }

    public ResponseDTO<List<ReloadResultVO>> queryReloadItemResult(String tag) {
        List<ReloadResultVO> reloadResultList = reloadResultDao.query(tag);
        return ResponseDTO.ok(reloadResultList);
    }


    /**
     * 通过标签更新标识符
     *
     * @param reloadForm
     * @return
     */
    public ResponseDTO<String> updateByTag(ReloadForm reloadForm) {
        ReloadItemEntity reloadItemEntity = reloadItemDao.selectById(reloadForm.getTag());
        if (null == reloadItemEntity) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        reloadItemEntity.setIdentification(reloadForm.getIdentification());
        reloadItemEntity.setUpdateTime(LocalDateTime.now());
        reloadItemEntity.setArgs(reloadForm.getArgs());
        reloadItemDao.updateById(reloadItemEntity);
        return ResponseDTO.ok();
    }
}
