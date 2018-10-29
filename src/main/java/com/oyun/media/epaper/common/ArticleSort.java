package com.oyun.media.epaper.common;

import com.google.common.collect.Sets;
import org.springframework.data.domain.Sort;

import java.util.Set;

/**
 * 排序生成器
 * Created by 瓦力.
 */
public class ArticleSort {
    public static final String DEFAULT_SORT_KEY = "releaseDate";

    public static final String CATALOG_KEY = "catalogName";


    private static final Set<String> SORT_KEYS = Sets.newHashSet(
        DEFAULT_SORT_KEY,
            "createTime",
            "recommend",
            CATALOG_KEY
    );

    public static Sort generateSort(String key, String directionKey) {
        key = getSortKey(key);
        Sort.Direction direction;

        if (directionKey == ""){
            direction = Sort.Direction.DESC;
        }else {
            direction = Sort.Direction.fromString(directionKey);
            if (direction == null||"".equals(direction)) {
                direction = Sort.Direction.DESC;
            }
        }
        return new Sort(direction, key);
    }

    public static String getSortKey(String key) {
        if (!SORT_KEYS.contains(key)) {
            key = DEFAULT_SORT_KEY;
        }

        return key;
    }
}
