package com.oyun.media.epaper.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @program: epaper
 * @description: 编辑推荐
 * @author: changzhen
 * @create: 2018-10-06 22:10
 **/
@Data
@Entity
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long articleId;

    @Column
    private String articleName;

    @Column
    private String author;

    @Column
    private Date releaseDate;

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequence;

    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column
    private Long pageId;

    @Column
    private String pageName;

    @Column
    private String pageImagePath;

    /**
     * 推荐新闻类型
     * 1-普通新闻
     * 2-图片新闻
     */
    @Column
    private int newsType = 1;

    private String showImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "recommend_image", joinColumns = @JoinColumn(name = "recommend_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id", referencedColumnName = "id"))
    private List<Attachment> contentImages;
}
