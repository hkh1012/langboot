package com.hkh.core.llm.capabilities.generation.text.qwen;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.QwenAiUtil;
import com.hkh.core.llm.capabilities.generation.QwenApis;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

/**
 * 通义千问AI web client
 * @author majb
 */
@Slf4j
@Component
public class QwenCompletionWebClient {
    private WebClient webClient;

    @Autowired
    private QwenAiUtil qwenAiUtil;

    @PostConstruct
    public void init(){
        log.info("qwen ai api web client init...");
        this.webClient = WebClient.builder()
            .defaultHeader("content-type", "application/json")
            .build();
    }

    public Flux<String> streamChatCompletion(JSONObject requestBody){
        log.info("Qwen streamChatCompletion 参数：{}",requestBody);
        String accessToken = qwenAiUtil.getAppKey();

        return webClient.post()
                .uri(QwenApis.COMPLETION_TEXT)
                .bodyValue(requestBody)
                .header("Authorization","Bearer " + accessToken)
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    ex.printStackTrace();
                    HttpStatusCode statusCode = ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("Qwen AI API error: {} {}", statusCode, res);
                    return Flux.error(new RuntimeException(res));
                });

    }

    public Flux<JSONObject> createFlux(JSONObject requestBody, QwenBizProcessor bizProcessor){
        log.info("Qwen createFlux 参数：{}",requestBody);
        Flux<JSONObject> flux = Flux.create(emitter -> {
            emitter.next(requestBody);
            emitter.complete();
        });

        flux.subscribe(
                jsonObject -> {
                    Flux<String> stringFlux = streamChatCompletion(requestBody);
                    stringFlux.subscribe(bizProcessor::bizProcess);
                },
                System.err::println,
                () -> System.out.println("emitter completed")
        );
        return flux;
    }

}
