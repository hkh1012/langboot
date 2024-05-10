package com.hkh.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.domain.ChatRequestLog;
import com.hkh.ai.service.ChatRequestLogService;
import com.hkh.ai.mapper.ChatRequestLogMapper;
import org.springframework.stereotype.Service;

/**
* @author huangkh
* @description 针对表【chat_request_log(对话请求日志)】的数据库操作Service实现
* @createDate 2023-12-23 13:55:32
*/
@Service
public class ChatRequestLogServiceImpl extends ServiceImpl<ChatRequestLogMapper, ChatRequestLog>
    implements ChatRequestLogService{

}




