package com.hkh.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.LinkedBlockingDeque;

@Data
@Getter
@Setter
public class FluxStreamDto {
    private LinkedBlockingDeque<String> textBlockingDeque;
    private LinkedBlockingDeque<String> audioBlockingDeque;
    private StringBuilder sb;
}