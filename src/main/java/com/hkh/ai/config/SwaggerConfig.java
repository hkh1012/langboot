package com.hkh.ai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PASSPORT API")
                        .version("1.0")
                        .description( "常笑passport接口文档")
                        .termsOfService("https://huangkh.cxjk.com")
                        .license(new License().name("Apache 2.0")
                                .url("https://license.cxjk.com")));
    }

    @Bean
    public GroupedOpenApi allApi(){
        return GroupedOpenApi.builder()
                .group("1全部")
                .packagesToScan("com.cxjk.passport.biz")
                .build();
    }

    @Bean
    public GroupedOpenApi appApi(){
        return GroupedOpenApi.builder()
                .group("2应用")
                .packagesToScan("com.cxjk.passport.biz.app")
                .build();
    }

    @Bean
    public GroupedOpenApi archApi(){
        return GroupedOpenApi.builder()
                .group("3权限")
                .packagesToScan("com.cxjk.passport.biz.arch")
                .build();
    }

    @Bean
    public GroupedOpenApi authApi(){
        return GroupedOpenApi.builder()
                .group("4认证")
                .packagesToScan("com.cxjk.passport.biz.auth")
                .build();
    }

    @Bean
    public GroupedOpenApi docApi(){
        return GroupedOpenApi.builder()
                .group("5文档")
                .packagesToScan("com.cxjk.passport.biz.doc")
                .build();
    }

    @Bean
    public GroupedOpenApi hrApi(){
        return GroupedOpenApi.builder()
                .group("6人事")
                .packagesToScan("com.cxjk.passport.biz.hr")
                .build();
    }

    @Bean
    public GroupedOpenApi logApi(){
        return GroupedOpenApi.builder()
                .group("7日志")
                .packagesToScan("com.cxjk.passport.biz.log")
                .build();
    }

}
