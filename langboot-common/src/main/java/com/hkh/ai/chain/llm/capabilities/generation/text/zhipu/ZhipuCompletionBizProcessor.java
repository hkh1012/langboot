package com.hkh.ai.chain.llm.capabilities.generation.text.zhipu;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.domain.CustomChatMessage;
import com.hkh.ai.domain.SysUser;
import com.hkh.ai.chain.service.ConversationService;
import com.knuddels.jtokkit.api.Encoding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class ZhipuCompletionBizProcessor {

    private final ConversationService conversationService;

    private final SysUser sysUser;

    private final CustomChatMessage request;

    private final SseEmitter sseEmitter;

    private final StringBuilder sb;

    private final Encoding enc;

    private final List<Integer> promptTokens;

    public void bizProcess(String item){
        System.out.println("智普流式输出:" +item);
        if (!"[DONE]".equals(item)){
            StreamCompletionResult resultObj = JSONObject.parseObject(item, StreamCompletionResult.class);
            String content = resultObj.getChoices().get(0).getDelta().getContent();
            try {
                if (StringUtils.isNotBlank(resultObj.getChoices().get(0).getFinish_reason())) {
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
                log.error("KimiCompletionBizProcessor--->>bizProcess异常", e);
                throw new RuntimeException(e);
            }
        }

    }
}
