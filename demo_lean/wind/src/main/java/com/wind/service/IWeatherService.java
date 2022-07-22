package com.wind.service;

import com.wind.entity.WeatherVO;

/**
 * IWeatherService
 *
 * @author: GJK
 * @date: 2022/7/16 12:20
 * @description:
 */
public interface IWeatherService {
    /**
     * 获取当前天气
     * @param city
     * @return
     */
    public WeatherVO getCurrentWeather(String city);
}
