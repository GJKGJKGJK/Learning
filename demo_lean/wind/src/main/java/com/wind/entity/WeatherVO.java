package com.wind.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * WeatherVO
 *
 * @author: GJK
 * @date: 2022/7/16 12:06
 * @description:
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherVO {

    private String high;

    private String low;

    private String weather;




}
