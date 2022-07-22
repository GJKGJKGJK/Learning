package com.wind.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Weather
 *
 * @author: GJK
 * @date: 2022/7/16 17:52
 * @description:
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Weather {

    private String date;
    private String high;
    private String low;
    private String type;

}
