package com.hkh.ai.domain.function;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * 地区天气
 */
@Data
@JsonClassDescription("weather of a location")
public class LocationWeather {

    @JsonPropertyDescription("city, for example: 常州,苏州,上海")
    @JsonProperty(required = true)
    public String location;

    @JsonPropertyDescription("date period of weather")
    @JsonProperty(required = true)
    public DatePeriod datePeriod;
}
