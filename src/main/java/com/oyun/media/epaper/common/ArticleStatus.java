package com.oyun.media.epaper.common;

/**
 * 文章状态
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-08-09 09:26
 **/
public enum  ArticleStatus {

    NOT_AUDITED(0),
    PASSES(1),
    DELETED(3);
    private int value;

    ArticleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
