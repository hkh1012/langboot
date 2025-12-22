package com.hkh.core.llm.capabilities.generation.structure.openai;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.hkh.core.llm.capabilities.generation.structure.StructureChatService;
import com.hkh.domain.entity.PlatformEntity;
import com.hkh.sa.base.module.support.platform.service.PlatformService;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OpenAiStructureChatService implements StructureChatService {

    @Value("${proxy.http.baseurl}")
    private String baseUrl;

    private final PlatformService platformService;

    public OpenAiStructureChatService(PlatformService platformService) {
        this.platformService = platformService;
    }
//         json_schema 格式必须为下面的格式
//        "json_schema": {
//            "name": "research_paper_extraction",
//            "schema": {
//                "type": "object",
//                "properties": {
//                    "title": { "type": "string" },
//                    "authors": {
//                        "type": "array",
//                        "items": { "type": "string" }
//                    },
//                    "abstract": { "type": "string" },
//                    "keywords": {
//                        "type": "array",
//                        "items": { "type": "string" }
//                    }
//                },
//                "required": ["title", "authors", "abstract", "keywords"],
//                "additionalProperties": false
//            },
//            "strict": true
//        }

    // 下面这种格式也是支持的
//        {
//            "type": "object",
//            "properties": {
    //            "steps": {
    //                "type": "array",
    //                "items": {
    //                    "$ref": "#/$defs/step"
    //                }
    //            },
    //            "final_answer": {
    //                "type": "string"
    //            }
//            },
//            "$defs": {
//            "step": {
//                "type": "object",
//                        "properties": {
//                    "explanation": {
//                        "type": "string"
//                    },
//                    "output": {
//                        "type": "string"
//                    }
//                },
//                "required": [
//                "explanation",
//                        "output"
//			],
//                "additionalProperties": false
//            }
//        },
//            "required": [
//            "steps",
//                    "final_answer"
//	],
//            "additionalProperties": false
//        }
    @Override
    public JSONObject structureCompletion(String systemPrompt, String userContent, JSONObject jsonSchema) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncoding(EncodingType.CL100K_BASE);
        List<Integer> promptTokens = enc.encode(userContent);
        System.out.println("promptTokens length == " + promptTokens.size());

        JSONObject requestBody = new JSONObject();
        requestBody.put("model","gpt-4o-2024-08-06");

        JSONArray messages = new JSONArray();
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role","system");
        systemMessage.put("content",systemPrompt);
        messages.add(systemMessage);
        JSONObject userMessage = new JSONObject();
        userMessage.put("role","user");
        userMessage.put("content",userContent);
        messages.add(userMessage);

        requestBody.put("messages",messages);

        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type","json_schema");

        responseFormat.put("json_schema",jsonSchema);
        requestBody.put("response_format",responseFormat);

        PlatformEntity platform = platformService.getByCode("openai");
        String dbToken = "";
        if (platform != null){
            dbToken = platform.getKeys();
        }
        HttpRequest httpRequest = new HttpRequest(UrlBuilder.of(baseUrl + "v1/chat/completions"));
        httpRequest.header("Authorization","Bearer " + dbToken);
        httpRequest.header("content-type","application/json");
        httpRequest.method(Method.POST);
        httpRequest.body(requestBody.toJSONString());
        String resultStr = httpRequest.execute().body();
        return JSONObject.parseObject(resultStr);
    }
}
