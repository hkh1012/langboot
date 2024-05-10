package com.hkh.ai.chain.split;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chain.split.chunk")
public class SplitterProperties {
    private String endspliter;
    private String qaspliter;
    private int size;
    private int overlay;
}
