package com.hkh.ai.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String[] swaggerUrls = {"/doc.html", "/webjars/**","/swagger-ui/**","/swagger-ui*/**","/swagger-resources/**", "/v3/api-docs/**"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true).maxAge(3600);

    }




}
