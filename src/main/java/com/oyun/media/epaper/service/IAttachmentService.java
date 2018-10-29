package com.oyun.media.epaper.service;


import com.oyun.media.epaper.common.ServiceMultiResult;
import com.oyun.media.epaper.domain.Attachment;
import com.oyun.media.epaper.form.DataTableSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author changzhen
 */
public interface IAttachmentService{

	/**
	 * 附件储存
	 * @param file
	 * @return
	 */
	Attachment save(MultipartFile file, HttpServletRequest request);

	Attachment uploadFile(MultipartFile file,String username) throws IOException;

	/**
	 * 根据url查询附件
	 * @param url
	 * @return
	 */
	Attachment getAttachmentByUrl(String url);

	/**
	 * 切换附件状态，转换附件临时状态
	 * @param attachment
	 * @return
	 */
	Attachment switchStatus(Attachment attachment);

	Attachment save(Attachment attachment);

	Attachment switchStatus(String url);

	Page<Attachment> getAllAttachmentByTemp(boolean temp,Pageable pageable);

	void delete(Attachment attachment);

	void deleteTempAttachment();

	List<Attachment> getAllAttachmentByTemp(boolean temp);

	ServiceMultiResult<Attachment> getAllAttachment(DataTableSearch searchBody);

	List<Attachment> getAttachmentsByUrl(Set<String> attachments);

	public void updateAttachemntsByUrl(List<Attachment> newAttachments,List<Attachment> attachments);
}