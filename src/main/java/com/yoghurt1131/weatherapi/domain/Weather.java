package com.yoghurt1131.weatherapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Weather implements Serializable {
    private int id;
    @JsonProperty("main")
    private String status;
    private String description;
    private String icon;
}
