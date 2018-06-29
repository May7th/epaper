package com.oyun.media.epaper.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: epaper
 * @description: 版面实体类
 * @author: changzhen
 * @create: 2018-05-25 11:55
 **/
@Entity
@Data
public class Page {

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageName;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp modifyTime;

//    @NotEmpty(message = "版面图片不能为空！")
    private String pageImagePath;

    private String description;

    private Long parentId;
    /**
     * 状态 0-有效 1-删除
     */
    private int state = 0;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "page_article", joinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    private List<Article> articleList;
}
