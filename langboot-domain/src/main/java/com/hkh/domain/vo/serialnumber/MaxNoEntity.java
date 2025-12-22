package com.hkh.domain.vo.serialnumber;

import lombok.Data;

/**
 * @author daxiangyu
 * @date 2024/6/21
 */
@Data
public class MaxNoEntity {

    private String noKey;

    private String reservedWord;

    private Integer noBit;

    private Long noMax;
}
