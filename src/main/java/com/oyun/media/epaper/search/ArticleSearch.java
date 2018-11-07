package com.oyun.media.epaper.search;

import lombok.Data;

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
    private String orderBy = "releaseDate";
    private String orderDirection = "desc";
    private Date releaseDate = null;

    private int start = 0;

    private int size = 5;


    public int getStart() {
        return start > 0 ? start : 0;
    }

    public int getSize() {
        if (this.size < 1) {
            return 5;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }
}
