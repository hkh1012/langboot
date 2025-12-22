package com.hkh.sa.base.module.support.reload;

import com.hkh.sa.base.common.util.SmartBeanUtil;
import com.hkh.sa.base.module.support.reload.core.AbstractSmartReloadCommand;
import com.hkh.domain.vo.reload.SmartReloadItem;
import com.hkh.domain.vo.reload.SmartReloadResult;
import com.hkh.sa.base.module.support.reload.dao.ReloadItemDao;
import com.hkh.sa.base.module.support.reload.dao.ReloadResultDao;
import com.hkh.domain.entity.reload.ReloadItemEntity;
import com.hkh.domain.entity.reload.ReloadResultEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * reload 操作
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2015-03-02 19:11:52
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Component
public class ReloadCommand extends AbstractSmartReloadCommand {

    @Resource
    private ReloadItemDao reloadItemDao;

    @Resource
    private ReloadResultDao reloadResultDao;

    /**
     * 读取数据库中SmartReload项
     *
     * @return List<ReloadItem>
     */
    @Override
    public List<SmartReloadItem> readReloadItem() {
        List<ReloadItemEntity> reloadItemEntityList = reloadItemDao.selectList(null);
        return SmartBeanUtil.copyList(reloadItemEntityList, SmartReloadItem.class);
    }


    /**
     * 保存reload结果
     *
     * @param smartReloadResult
     */
    @Override
    public void handleReloadResult(SmartReloadResult smartReloadResult) {
        ReloadResultEntity reloadResultEntity = SmartBeanUtil.copy(smartReloadResult, ReloadResultEntity.class);
        reloadResultDao.insert(reloadResultEntity);
    }
}
