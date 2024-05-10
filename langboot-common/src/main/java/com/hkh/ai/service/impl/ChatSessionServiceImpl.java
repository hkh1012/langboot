package com.hkh.ai.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkh.ai.domain.ChatSession;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.request.ChatSessionSaveRequest;
import com.hkh.ai.service.ChatSessionService;
import com.hkh.ai.mapper.ChatSessionMapper;
import com.hkh.ai.chain.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author huangkh
* @description 针对表【chat_session(用户会话)】的数据库操作Service实现
* @createDate 2023-06-20 16:47:01
*/
@Service
@AllArgsConstructor
public class ChatSessionServiceImpl extends ServiceImpl<ChatSessionMapper, ChatSession> implements ChatSessionService{

    private final ConversationService conversationService;
    @Override
    public void saveChatSession(ChatSessionSaveRequest request, SysUser sysUser) {
        if (StrUtil.isNotBlank(request.getSid())){
            UpdateWrapper<ChatSession> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("sid",request.getSid());
            updateWrapper.set("title",request.getTitle());
            update(updateWrapper);
        }else {
            ChatSession chatSession = new ChatSession();
            // 方便后期做分表处理
            String prefix = "000" + RandomUtil.randomInt(100);
            prefix = prefix.substring(prefix.length()-2);
            String sid =  prefix +"-"+ RandomUtil.randomString(47);
            chatSession.setSid(sid);
            chatSession.setModelId(request.getModelId());
            chatSession.setUserId(sysUser.getId());
            chatSession.setTitle(StrUtil.isNotBlank(request.getTitle()) ? request.getTitle() : "New Chat");
            chatSession.setCreateBy(sysUser.getUserName());
            chatSession.setCreateTime(LocalDateTime.now());
            save(chatSession);
        }

    }

    @Override
    public List<ChatSession> listByUserId(Integer userId) {
        QueryWrapper<ChatSession> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("create_time");
        List<ChatSession> chatSessionList = list(queryWrapper);
        return chatSessionList;
    }

    @Override
    public void removeBySid(String sid, SysUser sysUser) {
        UpdateWrapper<ChatSession> queryWrapper = new UpdateWrapper<>();
        queryWrapper.eq("sid",sid);
        queryWrapper.eq("user_id",sysUser.getId());
        this.remove(queryWrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("sid",sid);
        conversationService.removeByMap(map);
    }
}




