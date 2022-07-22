package com.wind.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wind.entity.FaildResultVO;
import com.wind.entity.WeatherVO;
import com.wind.service.IWeatherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * WeatherController
 *
 * @author: GJK
 * @date: 2022/7/16 12:04
 * @description:
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class WeatherController {


    private final IWeatherService weatherService;

    @GetMapping("/current/weather")
    public Object currentWeather(@RequestParam("city") String city){
        log.error("【GET】: /current/weather，the input param city :{}",city);
        if(StringUtils.isBlank(city)){
            return FaildResultVO.createErrors("参数为空");
        }
        try {
             WeatherVO currentWeather = weatherService.getCurrentWeather(city);
            if(currentWeather == null){
                return FaildResultVO.createErrors("获取当天日期失败");
            }
            return currentWeather;
        } catch (Exception e) {
            log.error("【GET】: /current/weather，the request failed");
            return FaildResultVO.createErrors("参数为空");
        }

    }

}
