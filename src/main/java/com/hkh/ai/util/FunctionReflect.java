package com.hkh.ai.util;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.ai.chain.callback.FunctionCaller;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class FunctionReflect {

    public static Object reflect(String methodName, JSONObject jsonNode){
        FunctionCaller functionCaller = new FunctionCaller();
        Method method = ReflectUtil.getMethod(FunctionCaller.class, methodName, new Class[]{JSONObject.class});
        try {
            Object invoke = method.invoke(functionCaller, jsonNode);
            return invoke;
        } catch (IllegalAccessException e) {
            log.error("FunctionReflect reflect occur illegal access exception,method name = {},jsonNode = {}",methodName,jsonNode,e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            log.error("FunctionReflect reflect occur invocation target exception,method name = {},jsonNode = {}",methodName,jsonNode,e);
            throw new RuntimeException(e);
        }
    }
}
