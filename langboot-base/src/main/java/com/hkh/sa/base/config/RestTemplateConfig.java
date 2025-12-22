package com.hkh.sa.base.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * http请求配置
 *
 * @Author 1024创新实验室: 罗伊
 * @Date 2022-05-30 21:22:12
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Configuration
public class RestTemplateConfig {

    @Value("${http.pool.max-total}")
    private Integer maxTotal;

    @Value("${http.pool.connect-timeout}")
    private Integer connectTimeout;

    @Value("${http.pool.read-timeout}")
    private Integer readTimeout;

    @Value("${http.pool.write-timeout}")
    private Integer writeTimeout;

    @Value("${http.pool.keep-alive}")
    private Integer keepAlive;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(this.clientHttpRequestFactory());
        List<HttpMessageConverter<?>> messageConverterList = restTemplate.getMessageConverters();
        messageConverterList.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverterList.addAll(this.converters());
        return restTemplate;
    }

    public List<HttpMessageConverter<?>> converters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        JsonbHttpMessageConverter jsonbHttpMessageConverter = new JsonbHttpMessageConverter();
        jsonbHttpMessageConverter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED));
        converters.add(converter);
        converters.add(jsonbHttpMessageConverter);
        return converters;
    }


    public JdkClientHttpRequestFactory clientHttpRequestFactory() {
        return new JdkClientHttpRequestFactory();
    }

    public OkHttpClient httpClientBuilder() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectionPool(this.pool())
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                .build();
    }

    public ConnectionPool pool() {
        return new ConnectionPool(maxTotal, keepAlive, TimeUnit.MILLISECONDS);
    }


    @Bean
    public X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Bean
    public SSLSocketFactory sslSocketFactory() {
        try {
            //信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

}
