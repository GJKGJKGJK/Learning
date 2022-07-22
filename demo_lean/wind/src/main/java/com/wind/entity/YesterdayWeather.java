package com.wind.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * YesterdayWeather
 *
 * @author: GJK
 * @date: 2022/7/16 17:50
 * @description:
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class YesterdayWeather extends Weather{
    private String fx;
    private String fl;
}
