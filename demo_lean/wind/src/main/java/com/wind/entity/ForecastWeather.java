package com.wind.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * ForecastWeather
 *
 * @author: GJK
 * @date: 2022/7/16 17:54
 * @description:
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ForecastWeather extends Weather{

    private String fengxiang;
    private String fengli;
}
