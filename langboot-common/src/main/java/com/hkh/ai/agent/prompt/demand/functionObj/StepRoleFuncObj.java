package com.hkh.ai.agent.prompt.demand.functionObj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class StepRoleFuncObj {

    @JsonPropertyDescription("role name")
    @JsonProperty(required = true)
    private String roleName;

}
