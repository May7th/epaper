package com.oyun.media.epaper.service;

import com.oyun.media.epaper.domain.Recommend;

import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-06 22:20
 **/
public interface IRecommendService {

    Recommend addRecommend(Long id,int type);

    void deleteRecommend(Long articleId);

    List<Recommend> findAllRecommendsByType(int type);

    List<Recommend> saveRecommendList(List<Recommend> recommendList);
}
