package com.hkh.domain.form.specialnoun;

import lombok.Data;

@Data
public class SpecialNounSaveRequest {

    private Integer id;

    private String pinyin;

    private String content;

    private Integer sort;

}
