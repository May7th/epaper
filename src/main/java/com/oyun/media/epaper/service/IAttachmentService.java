package com.oyun.media.epaper.service;


import com.oyun.media.epaper.domain.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 文件服务接口
 * @author 李文杰  E-mail:hhhtliwenjie@163.com
 * @version V1.0.0
 * 创建时间：2016年11月11日 下午5:17:31
 */
public interface IAttachmentService{

	/**
	 * 删除
	 * @param Attachment {@link Attachment}
	 */
	void delete(Attachment Attachment);

	/**
	 * 获取所有文件
	 * @param con 查询条件
	 * @return 所有文件
	 */
	Page<Attachment> find(String con, Pageable pageable);

	/**
	 * 根据文件地址删除文件
	 * @param urls 文件url,多个用逗号分隔
	 */
	void deleteByUrl(String urls);

	/**
	 * 根据文件地址查询文件
	 * @param url 文件url
	 * @return {@link Attachment}
	 */
	Attachment loadByUrl(String url);

	/**
	 * 获取所有临时文件
	 * @return 所有临时文件
	 */
	List<Attachment> listByTemp();

	/**
	 * 校验是否删除文件，如果文件已标记为临时文件，则删除，否则不删除
	 * @param urls 文件url,多个用逗号分隔
	 * @return 删除后的文件url集合,多个用逗号分隔
	 */
	String checkDelete(String urls);

	/**
	 * 校验是否添加文件，如果文件已经正常上传，则标记为永远
	 * @param urls 文件url,多个用逗号分隔
	 * @return 如果上传成功返回文件地址，否则返回null
	 */
	String checkAdd(String urls);

	/**
	 * 判断是否更新文件，并删除无效文件
	 * @param oldUrls 原文件url,多个用逗号分隔
	 * @param newUrls 新文件url,多个用逗号分隔
	 * @return 更新后的地址
	 */
	String checkUpdate(String oldUrls, String newUrls);

	/**
	 * 判断临时文件
	 * @param urls
	 * @return
	 */
	String checkTemp(String urls);
}