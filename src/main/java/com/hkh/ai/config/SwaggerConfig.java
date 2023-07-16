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
                        .title("langchain-springboot api")
                        .version("1.0")
                        .description( "接口文档")
                        .termsOfService("https://github.com/hkh1012/langchain-springboot")
                        .license(new License().name("Apache 2.0")
                                .url("https://license.cxjk.com")));
    }

    @Bean
    public GroupedOpenApi allApi(){
        return GroupedOpenApi.builder()
                .group("1全部")
                .packagesToScan("com.hkh.ai.controller")
                .build();
    }

}
