package com.hkh.ai.agent.prompt.demand.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class StepRoleFuncObj {

    @JsonPropertyDescription("role name")
    @JsonProperty(required = true)
    private String roleName;

}
