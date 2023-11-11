package com.hkh.ai.chain.llm.baiduqianfan;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.service.ConversationService;
import com.knuddels.jtokkit.api.Encoding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

/**
 * 百度千帆业务处理器
 * @author huangkh
 */
@Builder
@Data
@AllArgsConstructor
@Slf4j
public class BaiduQianFanCompletionBizProcessor {

    private final ConversationService conversationService;

    private final SysUser sysUser;

    private final CustomChatMessage request;

    private final SseEmitter sseEmitter;

    private final StringBuilder sb;

    private final Encoding enc;

    private final List<Integer> promptTokens;

    public void bizProcess(String item){
        log.info(item);
        StreamCompletionResult resultObj = JSONObject.parseObject(item, StreamCompletionResult.class);
        String content = resultObj.getResult();
        try {
            if (resultObj.getIs_end()) {
                this.getSseEmitter().send("[END]");
                String fullContent = this.getSb().toString();
                List<Integer> completionToken = this.getEnc().encode(fullContent);
                System.out.println("total token costs: " + (this.getPromptTokens().size() + completionToken.size()));
                this.getConversationService().saveConversation(this.getSysUser().getId(), this.getRequest().getSessionId(), this.getSb().toString(), "A");
            } else {
                if (content.contains("\n") || content.contains("\r")) {
                    content = content.replaceAll("\n", "<br>");
                    content = content.replaceAll("\r", "<br>");
                }
                if (content.contains(" ")) {
                    content = content.replaceAll(" ", "&nbsp;");
                }
                this.getSb().append(content);
                this.getSseEmitter().send(content);
            }
        } catch (IOException e) {
            log.error("BaiduQianFanCompletionBizProcessor--->>bizProcess异常", e);
            throw new RuntimeException(e);
        }

    }
}
