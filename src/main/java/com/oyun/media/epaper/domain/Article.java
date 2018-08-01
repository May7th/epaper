package com.oyun.media.epaper.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Column
    private String author;

    @Column(nullable = false,updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp modifyTime;

    @Lob
    private String coordinate;

    @Column(length = 100)
    private String title;

    @Column
    private String subTitle;

    @Lob
    private String content;

    @Lob
    private String contentHtml;

    /**
     * 状态 0-待审核 1-正常 2-删除
     */
    @Column
    private int state = 0;

    @Column
    private Long parentId;

    private Date releaseDate;
}
