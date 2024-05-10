package com.hkh.ai.chain.llm.capabilities.generation.text.baidu;

import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.capabilities.generation.BaiduQianFanUtil;
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
 * 百度千帆 web client
 * @author huangkh
 */
@Slf4j
@Component
public class BaiduQianFanCompletionWebClient {
    private WebClient webClient;

    @Autowired
    private BaiduQianFanUtil baiduQianFanUtil;


    @PostConstruct
    public void init(){
        log.info("baidu ai web client init...");
        this.webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
    }

    public Flux<String> streamChatCompletion(JSONObject requestBody){
        log.info("streamChatCompletion 参数：{}",requestBody);
        String url = baiduQianFanUtil.getUrl();
        String accessToken = baiduQianFanUtil.getAccessToken();
        return webClient.post()
                .uri(url + "?access_token=" + accessToken)
                .bodyValue(requestBody.toJSONString())
                .retrieve()
                .bodyToFlux(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatusCode statusCode = ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("Baidu API error: {} {}", statusCode, res);
                    return Mono.error(new RuntimeException(res));
                });

    }

    public Flux<JSONObject> createFlux(JSONObject requestBody, BaiduQianFanCompletionBizProcessor baiduQianFanCompletionBizProcessor){
        log.info("createFlux 参数：{}",requestBody);
        Flux<JSONObject> flux = Flux.create(emitter -> {
            emitter.next(requestBody);
            emitter.complete();
        });

        flux.subscribe(
                jsonObject -> {
                    Flux<String> stringFlux = streamChatCompletion(requestBody);
                    stringFlux.subscribe(baiduQianFanCompletionBizProcessor::bizProcess);
                },
                System.err::println,
                () -> System.out.println("emitter completed")
        );
        return flux;
    }

}
