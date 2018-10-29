package com.oyun.media.epaper.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-09-11 10:05
 **/
@Data
public class QueryData {

    private int limit;

    private int offset;

    private String order = "";

    private String sort = "";


    private String paperName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;


}
