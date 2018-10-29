package com.oyun.media.epaper.service.impl;

import com.oyun.media.epaper.domain.Article;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.domain.Page;
import com.oyun.media.epaper.domain.Recommend;
import com.oyun.media.epaper.repository.ArticleRepository;
import com.oyun.media.epaper.repository.PageRepository;
import com.oyun.media.epaper.repository.RecommendRepository;
import com.oyun.media.epaper.service.IRecommendService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-06 22:20
 **/
@Service
@Log4j2
public class RecommendServiceImpl implements IRecommendService {

    @Autowired
    private RecommendRepository recommendRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public Recommend addRecommend(Long id,int type){
        Recommend recommend;
        Article article = articleRepository.getOne(id);

        Page page = pageRepository.getOne(article.getParentId());

        article.setRecommend(type);

        if (recommendRepository.findByArticleId(id) == null){
            recommend = new Recommend();
        }else {
            recommend = recommendRepository.findByArticleId(id);
        }
        recommend.setArticleId(article.getId());
        recommend.setArticleName(article.getTitle());
        recommend.setAuthor(article.getAuthor());
        recommend.setReleaseDate(article.getReleaseDate());
        recommend.setPageId(page.getId());
        recommend.setPageName(page.getPageName());
        recommend.setPageImagePath(page.getPageImagePath());
        recommend.setNewsType(type);

        if (article.getContentImages().isEmpty()){
            recommend.setNewsType(1);
        }else{
            Attachment showAttachment = article.getContentImages().get(0);
            recommend.setShowImage(showAttachment.getUrl());
            List<Attachment> attachments = new ArrayList<>();
            article.getContentImages().forEach(attachment -> {
                attachments.add(attachment);
            });
            recommend.setContentImages(attachments);
        }

        articleRepository.save(article);

        return recommendRepository.save(recommend);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecommend(Long articleId) {
        Article article = articleRepository.getOne(articleId);

        Recommend recommend = recommendRepository.findByArticleId(articleId);
        if (recommend == null){

        }else {
            article.setRecommend(0);
            articleRepository.save(article);
            recommendRepository.delete(recommend);
        }

    }

    @Override
    public List<Recommend> findAllRecommendsByType(int type) {
        if (type == 0){
            return recommendRepository.findAll();
        }else{
            return recommendRepository.findAllByNewsType(type);
        }
    }

    @Override
    public List<Recommend> saveRecommendList(List<Recommend> recommendList) {

        return recommendRepository.saveAll(recommendList);

    }


}
