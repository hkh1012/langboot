package com.hkh.sa.base.module.support.loginlog;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkh.domain.common.PageResult;
import com.hkh.domain.common.ResponseDTO;
import com.hkh.domain.enums.base.UserTypeEnum;
import com.hkh.domain.enums.LoginLogResultEnum;
import com.hkh.sa.base.common.util.SmartPageUtil;
import com.hkh.domain.entity.login.LoginLogEntity;
import com.hkh.domain.form.login.LoginLogQueryForm;
import com.hkh.domain.vo.login.LoginLogVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录日志
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022/07/22 19:46:23
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
@Slf4j
public class LoginLogService {

    @Resource
    private LoginLogDao loginLogDao;

    /**
     *
     * @description 分页查询
     */
    public ResponseDTO<PageResult<LoginLogVO>> queryByPage(LoginLogQueryForm queryForm) {
        Page page = SmartPageUtil.convert2PageQuery(queryForm);
        List<LoginLogVO> logList = loginLogDao.queryByPage(page, queryForm);
        PageResult<LoginLogVO> pageResult = SmartPageUtil.convert2PageResult(page, logList);
        return ResponseDTO.ok(pageResult);
    }

    /**
     *
     * @description 添加
     */
    public void log(LoginLogEntity loginLogEntity) {
        try {
            loginLogDao.insert(loginLogEntity);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 查询上一个登录记录
     *
     *
     * @description 查询上一个登录记录
     */
    public LoginLogVO queryLastByUserId(Long userId, UserTypeEnum userTypeEnum, LoginLogResultEnum loginLogResultEnum) {
        return loginLogDao.queryLastByUserId(userId,userTypeEnum.getValue(), loginLogResultEnum.getValue());
    }

}
