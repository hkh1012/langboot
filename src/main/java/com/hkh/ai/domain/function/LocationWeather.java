package com.hkh.ai.domain.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * 地区天气
 */
@Data
public class LocationWeather {

    @JsonPropertyDescription("The temperature of the weather")
    @JsonProperty(required = true)
    public int temperature;

    @JsonPropertyDescription("description of the weather")
    @JsonProperty(required = true)
    public String description;

    @JsonPropertyDescription("City and state, for example: León, Guanajuato")
    public String location;

    @JsonPropertyDescription("The temperature unit, can be 'celsius' or 'fahrenheit'")
    @JsonProperty(required = true)
    public WeatherUnit unit;
}
