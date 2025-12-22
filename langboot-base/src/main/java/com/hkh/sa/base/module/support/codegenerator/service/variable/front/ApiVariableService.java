package com.hkh.sa.base.module.support.codegenerator.service.variable.front;

import com.hkh.domain.form.codegenerator.CodeGeneratorConfigForm;
import com.hkh.sa.base.module.support.codegenerator.service.variable.CodeGenerateBaseVariableService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author 1024创新实验室-主任:卓大
 * @Date 2022/9/29 17:20:41
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */

public class ApiVariableService extends CodeGenerateBaseVariableService {

    @Override
    public boolean isSupport(CodeGeneratorConfigForm form) {
        return true;
    }

    @Override
    public Map<String, Object> getInjectVariablesMap(CodeGeneratorConfigForm form) {
        Map<String, Object> variablesMap = new HashMap<>();

        return variablesMap;
    }
}
