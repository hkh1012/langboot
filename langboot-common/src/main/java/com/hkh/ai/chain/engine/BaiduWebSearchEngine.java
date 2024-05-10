package com.hkh.ai.chain.engine;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 百度网页搜索:坑货百度硬是没找到对应的搜索API，所以只能用网页搜索了
 * @author huangkh
 */
@Component
@AllArgsConstructor
@Slf4j
public class BaiduWebSearchEngine implements WebSearchEngine {
    @Override
    public InputStream search(String searchWord) {
        String url = "https://www.baidu.com/s?wd=" + searchWord;
        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(url));
        httpRequest.method(Method.GET);
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
        Document document = Jsoup.parse(_html);
        // 百度网页搜索结果放在 content_left 中
        Element results = document.getElementById("content_left");
        Elements elements = results.getElementsByClass("result");
        for (Element element : elements){
            Elements h3s = element.getElementsByTag("h3");
            Elements as = h3s.get(0).getElementsByTag("a");
            Element a = as.get(0);
            String href = a.attr("href");
            System.out.println(href);

            // 访问链接
            HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(href));
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
                // 如果包含百度安全验证，则跳过
                if (s.contains("<title>百度安全验证</title>")  || s.contains("301 Moved Permanently")){
                    continue;
                }
                // 只取一个结果就行
                Document doc = Jsoup.parse(s);
                Elements body = doc.getElementsByTag("body");
                String text = body.get(0).text();
                System.out.println("网络资料:\n" + text);
                resultText = resultText + "网络资料:\n" + text + "\n";
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultText;
    }

}
