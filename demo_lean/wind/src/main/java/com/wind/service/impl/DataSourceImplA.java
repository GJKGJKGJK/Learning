package com.wind.service.impl;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wind.entity.ForecastWeather;
import com.wind.entity.Weather;
import com.wind.entity.WeatherResultDTO;
import com.wind.entity.WeatherVO;
import com.wind.entity.YesterdayWeather;
import com.wind.service.IDataSource;
import com.wind.utils.HttpRequestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * DataSourceImplA
 *
 * @author: GJK
 * @date: 2022/7/16 16:28
 * @description:
 */
@Service("dataSourceImplA")
@Slf4j
public class DataSourceImplA implements IDataSource {

    public static final int SUCCESS = 1000;

    public static final String[] WEEK =  {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    @Value("${weather.datasourceA}")
    private String datasource;


    @Override
    public WeatherVO getCurrentWeather(String city) {
        String result = HttpRequestUtil.getForString(datasource + city);
        log.info("weather.datasourceA接口返回为: {}", result);
        if(result != null){
            JSONObject resultObject = JSON.parseObject(result);
            if(SUCCESS == resultObject.getInteger("status")){
                return parseCurrentWeather(resultObject.getString("data"));
            }
        }
        return null;
    }

    private WeatherVO parseCurrentWeather(String data) {
        if(StringUtils.isBlank(data)){
            return null;
        }
        String today = getToday();
        WeatherResultDTO weatherResult = JSON.parseObject(data, WeatherResultDTO.class);
        List<ForecastWeather> weatherList = weatherResult.getForecast();
        ForecastWeather forecastWeather = weatherList.stream().filter(e -> StringUtils.equals(e.getDate(), today)).findFirst().get();
        String temperatureHigh = forecastWeather.getHigh().substring(forecastWeather.getHigh().indexOf(" "));
        String temperatureLow = forecastWeather.getLow().substring(forecastWeather.getLow().indexOf(" "));
        return WeatherVO.builder().high(temperatureHigh).low(temperatureLow).weather(forecastWeather.getType()).build();
    }

    private String getToday() {
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        String dayOfWeek = WEEK[now.getDayOfWeek().getValue()];
        return dayOfMonth + "日" +dayOfWeek;
    }
}
