package com.hkh.ai.agent.prompt.function;

import com.hkh.ai.chain.prompt.PromptTemplate;

public class FunctionTypePrompt extends PromptTemplate {


    public static String prompt(String... args){
        FunctionTypePrompt functionTypePrompt = new FunctionTypePrompt();
        return functionTypePrompt.replaceArgs(args);
    }

    @Override
    public String replaceArgs(String... args) {
        String role = args[0];
        String stepName = args[1];
        String description = args[2];
        String prompt = message.replace("{{ role }}", role).replace("{{ stepName }}", stepName).replace("{{ description }}", description);
        return prompt;
    }

    private String message =
            """
                您当前的角色是{{ role }}，现在有一个{{ stepName }}任务,具体任务内容如下:{{ description }}。
                请分析完成以上任务，需要用哪一种工具去完成任务，如果任务内容涉及AI相关内容，如NLP自然语言理解、文本生成、文本总结、文本提取、文本生成图片、文本生成视频等内容，请回答AI，其他请回答TOOL，最终结果以json格式进行输出，以方便java fastjson库进行解析。
                    """;
}
