package com.wind.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wind.entity.WeatherVO;
import com.wind.service.IDataSource;
import com.wind.service.IWeatherService;

import lombok.RequiredArgsConstructor;

/**
 * WeatherServiceImpl
 *
 * @author: GJK
 * @date: 2022/7/16 12:20
 * @description:
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WeatherServiceImpl implements IWeatherService {


    @Value("${max.retry.times}")
    private int maxRetryTimes;

    private final Map<String, IDataSource> dataSourceMap;


    @Override
    public WeatherVO getCurrentWeather(String city) {
        int flag = 0;
        do {
            for (IDataSource dataSource : dataSourceMap.values()) {
                WeatherVO currentWeather = dataSource.getCurrentWeather(city);
                if (currentWeather != null) {
                    return currentWeather;
                }
            }
            flag++;
        } while (flag < maxRetryTimes);
        return null;
    }
}
