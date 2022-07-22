package com.wind.service;

import com.wind.entity.WeatherVO;

/**
 * IDataSource
 *
 * @author: GJK
 * @date: 2022/7/16 16:26
 * @description:
 */
public interface IDataSource {

    public WeatherVO getCurrentWeather(String city);
}
