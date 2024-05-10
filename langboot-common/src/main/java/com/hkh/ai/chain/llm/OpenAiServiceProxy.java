package com.hkh.ai.chain.llm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.time.Duration;
import java.util.concurrent.ExecutorService;

@Component
public class OpenAiServiceProxy {

    @Value("${proxy.enable}")
    private Boolean proxyEnable;

    @Value("${proxy.mode}")
    private String proxyMode;

    @Value("${proxy.socket.host}")
    private String proxyHost;

    @Value("${proxy.socket.port}")
    private String proxyPort;

    @Value("${chain.llm.openai.token}")
    private String apiToken;

    @Value("${proxy.http.baseurl}")
    private String baseUrl;

    public OpenAiService service(){
        return proxyEnable ? proxyService() : new OpenAiService(apiToken);
    }

    public OpenAiService proxyService(){
        ObjectMapper objectMapper = OpenAiService.defaultObjectMapper();
        OkHttpClient client = OpenAiService.defaultClient(apiToken, Duration.ZERO);
        Retrofit retrofit = OpenAiService.defaultRetrofit(client, objectMapper);
        if ("socket".equals(proxyMode)){
            System.getProperties().setProperty("https.proxyHost",proxyHost);
            System.getProperties().setProperty("https.proxyPort",proxyPort);
        }else if ("http".equals(proxyMode)){
            retrofit = retrofit.newBuilder().baseUrl(baseUrl).build();
        }
        OpenAiApi openAiApi = retrofit.create(OpenAiApi.class);
        ExecutorService executorService = client.dispatcher().executorService();
        OpenAiService service = new OpenAiService(openAiApi,executorService);
        return service;
    }
}
