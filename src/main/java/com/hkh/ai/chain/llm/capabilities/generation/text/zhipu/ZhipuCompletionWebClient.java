package com.hkh.ai.chain.llm.capabilities.generation.text.zhipu;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.BaiduQianFanUtil;
import com.hkh.ai.chain.llm.capabilities.generation.ZhipuAiUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 智普AI web client
 * @author huangkh
 */
@Slf4j
@Component
public class ZhipuCompletionWebClient {
    private WebClient webClient;

    @Autowired
    private ZhipuAiUtil zhipuAiUtil;

    @PostConstruct
    public void init(){
        log.info("zhipu ai api web client init...");
        this.webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
    }

    public Flux<String> streamChatCompletion(JSONObject requestBody){
        log.info("streamChatCompletion 参数：{}",requestBody);
        String url = zhipuAiUtil.getCompletionModel();
        String accessToken = zhipuAiUtil.getAccessToken();
        return webClient.post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE,"application/json")
                .header("Authorization",accessToken)
                .bodyValue(requestBody.toJSONString())
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatusCode statusCode = ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("ZhipuAI API error: {} {}", statusCode, res);
                    return Mono.error(new RuntimeException(res));
                });

    }

    public Flux<JSONObject> createFlux(JSONObject requestBody, ZhipuCompletionBizProcessor zhipuCompletionBizProcessor){
        log.info("createFlux 参数：{}",requestBody);
        Flux<JSONObject> flux = Flux.create(emitter -> {
            emitter.next(requestBody);
            emitter.complete();
        });

        flux.subscribe(
                jsonObject -> {
                    Flux<String> stringFlux = streamChatCompletion(requestBody);
                    stringFlux.subscribe(zhipuCompletionBizProcessor::bizProcess);
                },
                System.err::println,
                () -> System.out.println("emitter completed")
        );
        return flux;
    }

}
