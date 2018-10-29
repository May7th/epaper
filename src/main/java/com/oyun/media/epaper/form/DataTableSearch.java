package com.oyun.media.epaper.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-13 09:26
 **/
@Data
public class DataTableSearch {

    private int draw;

    private int start;
    private int length;

    private Integer temp;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTimeMax;

    private String oldName;

    private String direction;

    private String orderBy;


}
