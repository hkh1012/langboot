package com.hkh.ai.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码",title = "状态码")
    private String code;

    @Schema(description = "返回信息",title = "返回信息")
    private String message;

    @Schema(description = "返回数据",title = "返回数据")
    private T data;

    public ResultData() {
    }

    public ResultData(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>(ResultCodeEnum.SUCCESS);
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> success(String message) {
        ResultData<T> resultData = new ResultData<>(ResultCodeEnum.SUCCESS);
        resultData.setMessage(message);
        return resultData;
    }

    public static <T> ResultData<T> success(T data,String message) {
        ResultData<T> resultData = new ResultData<>(ResultCodeEnum.SUCCESS);
        resultData.setData(data);
        resultData.setMessage(message);
        return resultData;
    }

    public static <T> ResultData<T> fail(ResultCodeEnum resultCodeEnum, String message) {
        ResultData<T> resultData = new ResultData<>(resultCodeEnum);
        resultData.setMessage(message);
        return resultData;
    }

    public static <T> ResultData<T> fail(String code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        return resultData;
    }

}
