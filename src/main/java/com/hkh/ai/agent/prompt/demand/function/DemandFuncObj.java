package com.hkh.ai.agent.prompt.demand.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class DemandFuncObj {

    @JsonPropertyDescription("The roles ")
    @JsonProperty(required = true)
    private List<DemandRole> roles;

    @JsonPropertyDescription("The name of the step")
    @JsonProperty(required = true)
    private List<DemandStep> steps;
}
