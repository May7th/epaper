package com.oyun.media.epaper.common;

import lombok.NoArgsConstructor;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-06-01 16:26
 **/
public final class Const {

    private Const(){}

    public interface State{
        int SAVE = 0;
        int PASS = 1;
        int DELETE = 2;
    }

}
