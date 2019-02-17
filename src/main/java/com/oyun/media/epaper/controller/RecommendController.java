package com.oyun.media.epaper.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.domain.Recommend;
import com.oyun.media.epaper.service.IArticleService;
import com.oyun.media.epaper.service.IRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-06 22:33
 **/
@RestController
@RequestMapping("recommend")
public class RecommendController {

    @Autowired
    private IRecommendService recommendService;

    @Autowired
    private IArticleService articleService;

    @PostMapping("add/{type}")
    public ApiResponse addRecommend(@RequestParam(value="id", required = true) Long id,
                                    @PathVariable() int type){

        Recommend recommend = recommendService.addRecommend(id,type);

        if (recommend != null){
            return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
        }else {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_SUPPORTED_OPERATION);
        }
    }
    @PostMapping("delete")
    public ApiResponse deleteRecommend(@RequestParam(value="id", required = true) Long id){

        if (id >= 0){
            recommendService.deleteRecommend(id);
            return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
        }else {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_SUPPORTED_OPERATION);
        }
    }
    @GetMapping("list/{type}")
    public ApiResponse getRecommendList(@PathVariable(value = "type") int type){

        List<Recommend> recommendList = recommendService.findAllRecommendsByType(type);

        if (!recommendList.isEmpty()){
            return ApiResponse.ofSuccess(recommendList);
        }else {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_SUPPORTED_OPERATION);
        }
    }

    @PostMapping("saveList")
    public ApiResponse saveRecommendList(@RequestBody String recommendList){

        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Recommend.class);
        try {
            List<Recommend> list = objectMapper.readValue(recommendList, javaType);

            List<Recommend> recommends = recommendService.saveRecommendList(list);
            if (!recommends.isEmpty()){
                return ApiResponse.ofStatus(ApiResponse.Status.SUCCESS);
            }else {
                return ApiResponse.ofStatus(ApiResponse.Status.NOT_SUPPORTED_OPERATION);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.ofStatus(ApiResponse.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
