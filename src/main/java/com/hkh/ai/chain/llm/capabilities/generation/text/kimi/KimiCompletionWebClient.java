package com.hkh.ai.chain.llm.capabilities.generation.text.kimi;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.KimiAiUtil;
import com.hkh.ai.chain.llm.capabilities.generation.KimiApis;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

/**
 * 智普AI web client
 * @author huangkh
 */
@Slf4j
@Component
public class KimiCompletionWebClient {
    private WebClient webClient;

    @Autowired
    private KimiAiUtil kimiAiUtil;

    @PostConstruct
    public void init(){
        log.info("kimi ai api web client init...");
        this.webClient = WebClient.builder()
            .defaultHeader("content-type", "application/json")
            .build();
    }

    public Flux<String> streamChatCompletion(JSONObject requestBody){
        log.info("streamChatCompletion 参数：{}",requestBody);
        String accessToken = kimiAiUtil.getAppKey();
        return webClient.post()
                .uri(KimiApis.COMPLETION_TEXT)
                .bodyValue(requestBody)
                .header("Authorization","Bearer " + accessToken)
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    ex.printStackTrace();
                    HttpStatusCode statusCode = ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("Kimi AI API error: {} {}", statusCode, res);
                    return Flux.error(new RuntimeException(res));
                });

    }

    public Flux<JSONObject> createFlux(JSONObject requestBody, KimiCompletionBizProcessor kimiCompletionBizProcessor){
        log.info("createFlux 参数：{}",requestBody);
        Flux<JSONObject> flux = Flux.create(emitter -> {
            emitter.next(requestBody);
            emitter.complete();
        });

        flux.subscribe(
                jsonObject -> {
                    Flux<String> stringFlux = streamChatCompletion(requestBody);
                    stringFlux.subscribe(kimiCompletionBizProcessor::bizProcess);
                },
                System.err::println,
                () -> System.out.println("emitter completed")
        );
        return flux;
    }

}
