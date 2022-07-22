package com.wind.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * WeatherResulttDTO
 *
 * @author: GJK
 * @date: 2022/7/16 17:33
 * @description:
 */
@Data
@AllArgsConstructor
public class WeatherResultDTO {


    private YesterdayWeather yesterday;

    private String city;

    private List<ForecastWeather> forecast;

    private String ganmao;

    private String wendu;
}
