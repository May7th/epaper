package com.oyun.media.epaper.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyun.media.epaper.common.ApiResponse;
import com.oyun.media.epaper.domain.Catalog;
import com.oyun.media.epaper.domain.Recommend;
import com.oyun.media.epaper.service.ICatalogService;
import com.oyun.media.epaper.service.IUserService;
import com.oyun.media.epaper.utils.ConstraintViolationExceptionHandler;
import com.oyun.media.epaper.vo.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-10 16:24
 **/
@RestController
@RequestMapping("catalog")
public class CatalogController {

    @Autowired
    private ICatalogService catalogService;

    @GetMapping("list")
    private ApiResponse getCatalogList(){
        List<Catalog> catalogList= catalogService.getCatalogList();
        if (!catalogList.isEmpty()){
            return ApiResponse.ofSuccess(catalogList);
        }else {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_SUPPORTED_OPERATION);
        }
    }

    @PostMapping("add")
    private ResponseEntity<Response> addCatalog(@RequestBody Catalog catalog){
        try {
            Catalog SavedCatalog = catalogService.saveCatalog(catalog);
            return ResponseEntity.ok().body(new Response(true, "处理成功", SavedCatalog));
        }  catch (ConstraintViolationException e)  {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
    }

    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public ResponseEntity<Response> delete(@RequestParam(value="id", required = true) Long id) {
        try {
            catalogService.removeCatalog(id);
        } catch (Exception e) {
            return  ResponseEntity.ok().body( new Response(false, e.getMessage()));
        }
        return  ResponseEntity.ok().body( new Response(true, "删除成功"));
    }

    @PostMapping("saveList")
    public ApiResponse saveRecommendList(@RequestBody String catalogList){

        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, Catalog.class);
        try {
            List<Catalog> list = objectMapper.readValue(catalogList, javaType);

            List<Catalog> recommends = catalogService.saveCatalogList(list);
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
