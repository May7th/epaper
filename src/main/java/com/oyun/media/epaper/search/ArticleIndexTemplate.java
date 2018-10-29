package com.oyun.media.epaper.search;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @program: epaper
 * @description: 文章模板索引类
 * @author: changzhen
 * @create: 2018-08-07 23:31
 **/
@Data
public class ArticleIndexTemplate {

    private Long articleId;

    private String title;

    private String author;

    private Long catalogId;

    @Field( type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "date_time_no_millis or strict_date_time_no_millis||yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date releaseDate;

    @Field( type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "date_time_no_millis or strict_date_time_no_millis||yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;

    @Field( type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "date_time_no_millis or strict_date_time_no_millis||yyyy-MM-dd||epoch_millis")
    private Date auditTime;

    private String content;

    private String subTitle;

    private String parentName;

    private int state;

    private int recommend;

}
