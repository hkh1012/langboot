package com.hkh.ai.agent.prompt.demand.functionObj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class DemandFuncObj {

    @JsonPropertyDescription("The list of roles")
    @JsonProperty(required = true)
    private List<DemandRoleFuncObj> roles;

    @JsonPropertyDescription("The list of steps")
    @JsonProperty(required = true)
    private List<DemandStepFunObj> steps;
}
