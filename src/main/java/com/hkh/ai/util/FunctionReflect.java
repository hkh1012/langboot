package com.hkh.ai.util;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.hkh.ai.chain.function.FunctionCaller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FunctionReflect {

    public static Object reflect(String methodName, JSONObject jsonNode){
        FunctionCaller functionCaller = new FunctionCaller();
        Method method = ReflectUtil.getMethod(FunctionCaller.class, methodName, new Class[]{JSONObject.class});
        try {
            Object invoke = method.invoke(functionCaller, jsonNode);
            return invoke;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
