package com.hkh.ai.chain.plugin.search.engine;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.RandomUtil;
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

@Component
@AllArgsConstructor
@Slf4j
public class BingWebSearchEngine implements WebSearchEngine{
    @Override
    public InputStream search(String searchWord) {
        String url = "https://cn.bing.com/search?q=" + searchWord + "&rdr=1&rdrig=" + RandomUtil.randomString(32) + "&mkt=zh-CN";
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
        Element ul = document.getElementById("b_results");
        Elements lis = ul.getElementsByTag("li");
        Elements h2 = lis.get(0).getElementsByTag("h2");
        Elements as = h2.get(0).getElementsByTag("a");
        String href = as.get(0).attr("href");
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

            Document doc = Jsoup.parse(s);
            Elements body = doc.getElementsByTag("body");
            String text = body.get(0).text();
            System.out.println("网络资料:\n" + text);
            resultText = resultText + "网络资料:\n" + text + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultText;
    }
}
