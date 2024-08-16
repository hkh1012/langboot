package com.hkh.ai.chain.llm.capabilities.generation.vision.openai;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.llm.OpenAiServiceProxy;
import com.hkh.ai.chain.llm.capabilities.generation.vision.VisionChatService;
import com.hkh.ai.config.SysConfig;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * OpenAi 图文多模态实现
 * @author huangkh
 */
@Service
@Slf4j
public class OpenAiVisionChatService implements VisionChatService {

    @Value("${chain.llm.openai.token}")
    private String apiToken;

    @Value("${proxy.socket.host}")
    private String host;

    @Value("${proxy.socket.port}")
    private String port;

    @Autowired
    private SysConfig sysConfig;

    private WebClient webClient;

    @Autowired
    private OpenAiServiceProxy openAiServiceProxy;

    @PostConstruct
    public void init(){
        log.info("openai api web client init...");
        // 备注：openai-java 库最新版本仍未集成vision api,待新版本集成后需要统一成 OpenAiServiceProxy 的方式
        HttpClient httpClient = HttpClient.create()
                .proxy(typeSpec -> {
                    typeSpec.type(ProxyProvider.Proxy.HTTP)
                            .address(new InetSocketAddress(host,Integer.parseInt(port)))
                    ;
                });

        ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);

        webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .clientConnector(clientHttpConnector)
                .build();
    }

    @Override
    public String visionCompletion(String content, List<String> imageUrlList) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(content);
        System.out.println("promptTokens length == " + promptTokens.size());

        JSONObject textJson = new JSONObject();
        textJson.put("type","text");
        textJson.put("text",content);

        JSONArray contentArray = new JSONArray();
        contentArray.add(textJson);

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(now);
        for (String imageUrl : imageUrlList){
            // 如果传的是图片链接，openai无法访问国内的url地址，所以需要将图片转换为base64
            String fileName = System.currentTimeMillis() + ".png";
            File destFile = new File(sysConfig.getUploadPath() + File.separator + "image" + File.separator + dateStr + File.separator + fileName);
            File file = HttpUtil.downloadFileFromUrl(imageUrl, destFile);
            String base64Url = "data:image/png;base64," + Base64.encode(file);

            JSONObject imageUrlJson = new JSONObject();
            imageUrlJson.put("url",base64Url);

            JSONObject imageJson = new JSONObject();
            imageJson.put("type","image_url");
            imageJson.put("image_url",imageUrlJson);
            contentArray.add(imageJson);
        }

        JSONObject visionMessage = new JSONObject();
        visionMessage.put("role","user");
        visionMessage.put("content",contentArray);

        // 构建 message
        JSONArray messages = new JSONArray();
        messages.add(visionMessage);



        // 构建请求体
        JSONObject body = new JSONObject();
        body.put("messages",messages);
        body.put("model","gpt-4o");
//        body.put("request_id", UUID.fastUUID().toString(true));


        Mono<String> response = webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer " + apiToken)
                .header("content-type", "application/json")
                .bodyValue(body.toJSONString())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> {
                    ex.printStackTrace();
                    HttpStatusCode statusCode = ex.getStatusCode();
                    String res = ex.getResponseBodyAsString();
                    log.error("Openai API error: {} {}", statusCode, res);
                    return Mono.error(new RuntimeException(res));
                });
        String responseBody = null;
        try {
            responseBody = response.toFuture().get();
            log.info("open ai vision response ==> {}",responseBody);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        ChatCompletionResult result = JSONObject.parseObject(responseBody, ChatCompletionResult.class);
        return result.getChoices().get(0).getMessage().getContent();
    }
}
