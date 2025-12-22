package com.hkh.core.llm.capabilities.generation.structure.baidu;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hkh.core.llm.capabilities.generation.structure.StructureChatService;
import com.hkh.core.llm.capabilities.generation.text.baidu.BaiduQianFanTextChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@AllArgsConstructor
public class BaiduStructureChatService implements StructureChatService {

    private final BaiduQianFanTextChatService baiduQianFanTextChatService;
    @Override
    public JSONObject structureCompletion(String systemContent, String userContent, JSONObject jsonSchema) {
        String result = baiduQianFanTextChatService.blockCompletion(systemContent + "\n" + userContent + "\n" + jsonSchema.toJSONString());
        // 正则表达式匹配非标准格式的 JSON 对象
        String regex = "\\{[^}]*\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(result);

        if (matcher.find()) {
            String nonStandardJson = matcher.group();
            System.out.println("Extracted non-standard JSON: " + nonStandardJson);

            // 将非标准 JSON 转换为标准 JSON
            String standardJson = convertToStandardJson(nonStandardJson);
            System.out.println("Standard JSON: " + standardJson);
            return JSONObject.parseObject(standardJson);
        } else {
            System.out.println("No JSON object found in the input string.");
        }
        return null;
    }

    private static String convertToStandardJson(String nonStandardJson) {
        // 去除最后一个字段后可能存在的逗号
        String jsonWithoutTrailingComma =
                nonStandardJson.replaceAll(",\\s*}", "}")
                        .replaceAll("\\\\\\{","{")
                        .replaceAll("\\\\}\"","}")
                        .replaceAll("\\\\\"", "\"")
                        .replaceAll("\"\\{","{")
                        .replaceAll("}\"","}");

        // 替换单引号为双引号
        String jsonWithDoubleQuotes = jsonWithoutTrailingComma.replace("'", "\"");

        // 使用 Jackson 解析并格式化 JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonWithDoubleQuotes);
            return objectMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
