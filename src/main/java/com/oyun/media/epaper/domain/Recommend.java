package com.oyun.media.epaper.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Lob
    private String articleName;

    @Column
    private String author;

    @Column
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
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
