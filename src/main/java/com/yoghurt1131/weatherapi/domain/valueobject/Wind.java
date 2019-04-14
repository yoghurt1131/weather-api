package com.yoghurt1131.weatherapi.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Wind {

    // Wind speed
    @JsonProperty("speed")
    private float speed;

    // Wind direction, degrees (meteorological)
    @JsonProperty("deg")
    private float direction;
}
