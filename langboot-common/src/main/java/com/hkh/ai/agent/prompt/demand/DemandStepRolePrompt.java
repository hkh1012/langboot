package com.hkh.ai.agent.prompt.demand;

import com.hkh.ai.chain.prompt.PromptTemplate;

public class DemandStepRolePrompt extends PromptTemplate {

    public static String prompt(String... args){
        DemandStepRolePrompt demandStepRolePrompt = new DemandStepRolePrompt();
        return demandStepRolePrompt.replaceArgs(args);
    }

    @Override
    public String replaceArgs(String... args) {
        String agentField = args[0];
        String propose = args[1];
        String roles = args[2];
        String step = args[3];
        String prompt =
                message.replace("{{ agentField }}", agentField)
                .replace("{{ propose }}", propose)
                .replace("{{ roles }}", roles)
                .replace("{{ step }}", step);
        return prompt;
    }

    private String message =
            """ 
            {{ agentField }}，我们的最终目标是：{{ propose }}，请从角色列表({{ roles }})中选择一个合适的角色担当负责人完成以下任务：
            {{ step }}。
            最终结果以json格式进行输出，以方便java fastjson库进行解析。 
            """;
}
