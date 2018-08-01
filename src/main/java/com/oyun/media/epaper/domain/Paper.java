package com.oyun.media.epaper.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: epaper
 * @description: 报刊的实体类
 * @author: changzhen
 * @create: 2018-05-23 16:23
 **/

@Entity
@Data
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp createTime;

    @Column(nullable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Timestamp modifyTime;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date releaseDate;

    @NotEmpty
    @Column
    private String paperName;

    /**
     * 状态 0-有效 1-删除
     */
    private int state;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "paper_page", joinColumns = @JoinColumn(name = "paper_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "page_id", referencedColumnName = "id"))
    private List<Page> pageList;
}
