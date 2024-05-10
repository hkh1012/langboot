package com.hkh.ai.chain.engine;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;

@Component
@Slf4j
public class GoogleWebSearchEngine implements WebSearchEngine{

    @Value("${google.search.token}")
    private String appKey;
    @Value("${google.search.cx}")
    private String cx;
    @Value("${proxy.socket.host}")
    private String proxyHost;

    @Value("${proxy.socket.port}")
    private Integer proxyPort;
    @Override
    public InputStream search(String searchWord) {
        String url = "https://www.googleapis.com/customsearch/v1?key=" + appKey + "&cx=" + cx + "&q=" + searchWord;
        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(url));
        httpRequest.method(Method.GET);
        httpRequest.setProxy(new Proxy(Proxy.Type.SOCKS, new java.net.InetSocketAddress(proxyHost,proxyPort)));
        HttpResponse execute = httpRequest.execute();
        InputStream inputStream = execute.bodyStream();
        return inputStream;
    }

    @Override
    public String load(InputStream inputStream) {
        String resultText = "";
        StringBuffer stringBuffer = new StringBuffer();
        try (InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(reader)){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String _html = stringBuffer.toString();
        JSONObject jsonObject = JSONObject.parseObject(_html);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        if (jsonArray!=null && jsonArray.size()>0){
            JSONObject item = jsonArray.getJSONObject(0);
            // 访问链接
            HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(item.getString("link")));
            httpRequest.method(Method.GET);
            httpRequest.setFollowRedirects(true);
            HttpResponse execute = httpRequest.execute();
            InputStream is = execute.bodyStream();
            StringBuffer sb = new StringBuffer();
            try (InputStreamReader reader = new InputStreamReader(is, "UTF-8");
                 BufferedReader br = new BufferedReader(reader)){
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String s = sb.toString();

                Document doc = Jsoup.parse(s);
                Elements body = doc.getElementsByTag("body");
                String text = body.get(0).text();
                System.out.println("网络资料:\n" + text);
                resultText = "网络资料:\n" + text + "\n";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultText;
    }
}
