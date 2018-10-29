package com.oyun.media.epaper.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Date;
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

    @NotEmpty(message = "版面图片不能为空！")
    private String pageImagePath;

    @Lob
    private String description;

    private Long parentId;
    /**
     * 状态 0-有效 1-删除
     */
    private int state = 0;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date releaseDate;


    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @Lazy(value = false)
    @JoinTable(name = "page_article", joinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    private List<Article> articleList;
}
