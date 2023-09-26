package com.hkh.ai.agent.prompt.demand.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class DemandRole {

    @JsonPropertyDescription("The name of the role")
    @JsonProperty(required = true)
    public String roleName;

    @JsonPropertyDescription("The description of the role")
    @JsonProperty(required = true)
    public String duty;

}
