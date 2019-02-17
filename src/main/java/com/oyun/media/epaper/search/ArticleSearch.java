package com.oyun.media.epaper.search;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 文章请求参数结构体
 */
@Data
public class ArticleSearch {
    private String catalogName = "";
    private Long catalogId = -1L;
    private int recommend = -1;
    private int state = -1;
    private String keywords;
    private String wordType;
    private String orderBy = "releaseDate";
    private String orderDirection = "asc";
    @DateTimeFormat(pattern ="yyyy-mm-dd")
    private Date releaseDate = null;

    private int start = 0;

    private int size = 10;


    public int getStart() {
        return start > 0 ? start : 0;
    }

    public int getSize() {
        if (this.size < 1) {
            return 10;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }
}
