package com.oyun.media.epaper.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: epaper
 * @description: 文章实体类
 * @author: changzhen
 * @create: 2018-05-25 11:59
 **/
@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String author;

    @Column(nullable = false, updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    private String createUsername;

    @Column(nullable = true)
    private Timestamp auditTime;

    private String auditUsername;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp modifyTime;

    @Lob
    private String coordinate;

    @Lob
    private String title;

    @Lob
    private String subTitle;

    @Lob
    private String content;

    @Lob
    private String contentHtml;

    /**
     * 文章状态
     * 0-待审核
     * 1-已审核
     * 2-删除
     */
    @Column
    private int state = 0;

    @Column
    private Long parentId;

    @Column
    private String parentName;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date releaseDate;

    /**
     * 是否推荐阅读
     * 0-未推荐
     * 1-已推荐
     * 2-图文推荐
     */
    private int recommend = 0;

    private Integer readSize = 0;

    @OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_image", joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id", referencedColumnName = "id"))
    private List<Attachment> contentImages;

}
