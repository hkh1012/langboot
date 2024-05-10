package com.hkh.ai.chain.llm.capabilities.generation.function;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ChatFunctionObject {

    private String name;
    private String description;
    private FunctionParameters parameters;
    private String[] required;

    public ChatFunctionObject build(String functionName, String functionDescription, List<String> filedNameList,List<String> filedTypeList,List<String> fieldDescriptionList, String[] required){
        ChatFunctionObject chatFunctionObject = new ChatFunctionObject();
        chatFunctionObject.setName(functionName);
        chatFunctionObject.setDescription(functionDescription);
        Map<String, FunctionParametersFieldValue> properties = new HashMap<>();
        for (int i = 0; i < filedNameList.size(); i++) {
            properties.put(filedNameList.get(i),new FunctionParametersFieldValue(filedTypeList.get(i),fieldDescriptionList.get(i)));
        }
        FunctionParameters functionParameters = new FunctionParameters("object",properties);
        chatFunctionObject.setParameters(functionParameters);
        chatFunctionObject.setRequired(required);
        return chatFunctionObject;
    }
}
