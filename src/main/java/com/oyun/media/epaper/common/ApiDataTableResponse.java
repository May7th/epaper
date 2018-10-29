package com.oyun.media.epaper.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-13 09:16
 **/
@Getter
@Setter
public class ApiDataTableResponse extends ApiResponse {

    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    public ApiDataTableResponse(ApiResponse.Status status){
        this(status.getCode(),status.getStandardMessage(),null);
    }

    public ApiDataTableResponse(int code,String message,Object data){
        super(code, message, data);
    }
}
