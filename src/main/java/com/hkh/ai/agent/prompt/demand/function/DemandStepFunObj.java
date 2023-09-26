package com.hkh.ai.agent.prompt.demand.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class DemandStepFunObj {

    @JsonPropertyDescription("The name of the step")
    @JsonProperty(required = true)
    public String stepName;

    @JsonPropertyDescription("The description of the step")
    @JsonProperty(required = true)
    public String description;
}
