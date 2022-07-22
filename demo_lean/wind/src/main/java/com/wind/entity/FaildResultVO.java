package com.wind.entity;

import lombok.Data;

/**
 * FaildResult
 *
 * @author: GJK
 * @date: 2022/7/16 18:38
 * @description:
 */
@Data
public class FaildResultVO {

    private Boolean success;

    private String message;

    private Integer code;

    public static FaildResultVO createErrors(String message){
        FaildResultVO faildResultVO = new FaildResultVO();
        faildResultVO.setSuccess(false);
        faildResultVO.setCode(201);
        faildResultVO.setMessage(message);
        return faildResultVO;
    }
}
