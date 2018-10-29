package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-05-25 15:02
 **/
public interface RecommendRepository extends JpaRepository<Recommend,Long>{

    Recommend findByArticleId(Long id);

    List<Recommend> findAllByNewsType(int type);


}
