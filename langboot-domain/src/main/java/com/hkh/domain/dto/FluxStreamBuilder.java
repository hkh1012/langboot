package com.hkh.domain.dto;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class FluxStreamBuilder {
    public static FluxStreamDto build() {
        FluxStreamDto fluxStreamDto = new FluxStreamDto();
        LinkedBlockingDeque<String> textDeque = new LinkedBlockingDeque<>();
        LinkedBlockingDeque<String> audioDeque = new LinkedBlockingDeque<>();
        StringBuilder sb = new StringBuilder();
        fluxStreamDto.setTextBlockingDeque(textDeque);
        fluxStreamDto.setAudioBlockingDeque(audioDeque);
        fluxStreamDto.setSb(sb);
        return fluxStreamDto;
    }


}
