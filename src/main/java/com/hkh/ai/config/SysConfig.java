package com.hkh.ai.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class SysConfig {

    @Value("${upload.path}")
    private String uploadPath;
}
