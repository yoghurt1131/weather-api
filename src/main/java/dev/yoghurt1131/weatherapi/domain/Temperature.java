package dev.yoghurt1131.weatherapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class Temperature implements Serializable {
    private double temp;
    private int pressure;
    private int humidity;
    private double tempMax;
    private double tempMin;
}
