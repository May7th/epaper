package com.oyun.media.epaper.domain;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 附件对象
 *
 * @author changzhen
 */
@Entity
@Data
public class Attachment{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	/**
	 * 附件上传后生成的名称
	 */
	@Column(length = 64)
	@NotEmpty
	@Length(max = 64)
	private String name;
	
	/**
	 * 附件原始的名称
	 */
	@Column(name="old_name")
	@NotEmpty
	@Length(max = 255)
	private String oldName;
	/**
	 * 附件的存放地址,相对地址
	 */
	@Column(length = 128)
	@NotEmpty
	@Length(max = 128)
	private String url;
	/**
	 * 附件的类型，这个类型和contentType类型一致
	 */
	@Column(length = 32)
	@NotEmpty
	@Length(max = 32)
	private String type;
	/**
	 * 附件的后缀名
	 */
	@Column(length = 16)
	@NotEmpty
	@Length(max = 16)
	private String suffix;
	/**
	 * 附件的大小 单位b
	 */
	@Column(name="attach_size")
	private long size;
	
	/**
	 * 是否是图片
	 */
	@Column(name="img")
	private boolean img;
	
	/**
	 * 上传时间
	 */
	@Column(name="upload_date")
	private LocalDateTime uploadDate;
	/**
	 * 上传用户账号
	 */
	@Column(length = 64)
	@Length(max = 64)
	private String username;
	/**
	 * 扩展字段
	 */
	@Lob
	private String attr;

	/**
	 * 是否是临时文件，临时文件可以删除
	 */
	private boolean temp;

	/**
	 * 图片是否剪裁
	 */
	private boolean rate;

	/**
	 * 原图片的大小 单位b
	 */
	@Column(name="attach_rate_size")
	private long rateSourceSize;

	/**
	 * 原图片存放地址,相对地址
	 */
	@Column(length = 128)
	@Length(max = 128)
	private String rateSourceUrl;

	/**
	 * 图片md5值
	 */
	@Length(max=128)
	@Column(length = 128)
	private String md5;
}
