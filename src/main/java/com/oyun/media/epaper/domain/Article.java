package com.oyun.media.epaper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Column(nullable = false,updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column
    private Timestamp modifyTime;

    @NotEmpty
    @Column(length = 100)
    private String coordinate;

    @Column(length = 100)
    private String title;

    @Column
    private String content;

    @Column
    private String contentHtml;

    /**
     * 状态 0-有效 1-删除
     */
    @Column
    private int state = 0;

    @Column
    private Long parentId;

    @Column
    private Date releaseDate;

}
