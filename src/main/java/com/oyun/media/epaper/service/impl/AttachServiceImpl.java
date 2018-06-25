//package com.oyun.media.epaper.service.impl;
//
//import com.oyun.media.epaper.domain.Attachment;
//import com.oyun.media.epaper.repository.AttachmentRepository;
//import com.oyun.media.epaper.service.IAttachmentService;
//import org.agile.context.SystemContextUtil;
//import org.agile.dao.dto.*;
//import org.agile.lifecycle.context.SpringI18nUtil;
//import org.agile.lifecycle.model.Attachment;
//import org.agile.lifecycle.service.IAttachmentService;
//import org.agile.system.dao.IAttachmentDao;
//import org.agile.utils.FileUtil;
//import org.agile.utils.StringUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.inject.Inject;
//import java.io.File;
//import java.util.List;
//
//
//
//@Service("AttachmentService")
//@Transactional(readOnly = true,rollbackFor = Exception.class)
//public class AttachmentServiceImpl implements IAttachmentService {
//
//	private static final long serialVersionUID = 2514211419019188861L;
//
//	@Autowired
//	private AttachmentRepository attachmentRepository;
//	/**
//	 * 分隔符
//	 */
//	private String regex = ",";
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public Attachment add(Attachment attachment) {
//		if(attachment == null || loadByUrl(attachment.getUrl()) !=null){
//			//上传文件错误
//			throw new RuntimeException("Attachment.error");
//		}
//		return attachmentRepository.save(attachment);
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void update(Attachment Attachment) {
//		if(Attachment != null){
//			attachmentRepository.save(Attachment);
//		}
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void delete(Long id) {
//		Attachment Attachment = attachmentRepository.getOne(id);
//		if(Attachment != null){
//			attachmentRepository.deleteById(id);
//			deleteFile(Attachment);
//		}
//	}
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public void delete(Attachment attachment) {
//		if(attachment != null){
//			attachmentRepository.delete(attachment);
//			deleteFile(attachment);
//		}
//	}
//
//	private void deleteFile(Attachment attachment){
//		String fileDir = SystemContextUtil.getFileDir();
//		FileUtil.delFile(new File(fileDir+Attachment.getUrl()));
//		if(Attachment.isRate()){
//			FileUtil.delFile(new File(fileDir+Attachment.getRateSourceUrl()));
//		}
//	}
//
//	@Override
//	public Attachment load(Long id) {
//		return attachmentRepository.getOne(id);
//	}
//
//	@Override
//	public Page<Attachment> find(String con, IPagerAlias alias) {
//		return attachmentRepository.find(con,alias);
//	}
//
//	@Override
//	public List<Attachment> list(ICriteriaAlias criteriaAlias, IOrderBy orderBy) {
//		return attachmentRepository.list(criteriaAlias, orderBy);
//	}
//
//	@Override
//	public Pager<Attachment> find(IPagerAlias pagerAlias, ICriteriaAlias criteriaAlias,IOrderBy orderBy) {
//		return attachmentRepository.find(pagerAlias,criteriaAlias,orderBy);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public void deleteByUrl(String urls) {
//		if(StringUtils.isNotBlank(urls)){
//			for(String url:urls.split(regex)){
//				if(StringUtils.isBlank(url)) {
//					continue;
//				}
//				Attachment Attachment = this.loadByUrl(url);
//				if(Attachment != null){
//					attachmentRepository.delete(getEqCriteria("url",url));
//					deleteFile(Attachment);
//				}
//			}
//		}
//	}
//
//
//	@Override
//	public Attachment loadByUrl(String url) {
//		return attachmentRepository.load(getEqCriteria("url",url));
//	}
//
//	@Override
//	public List<Attachment> listByTemp() {
//		return attachmentRepository.list(getEqCriteria("temp",true),null);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public String checkDelete(String urls) {
//		StringBuilder sb = new StringBuilder();
//		//判断原文件是否已标记为临时文件，如果已标记，则删除文件
//		if(StringUtils.isNotBlank(urls)){
//			for(String url : urls.split(regex)){
//				if(StringUtils.isBlank(url)) {
//					continue;
//				}
//				//处理图片
//				Attachment Attachment = this.loadByUrl(url);
//				if(Attachment != null){
//					//删除临时文件
//					if(Attachment.isTemp()){
//						this.delete(Attachment);
//					}else {
//						if(sb.length() > 0){
//							sb.append(regex);
//						}
//						sb.append(url);
//					}
//				}
//			}
//		}
//		return sb.toString();
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public String checkAdd(String urls) {
//		StringBuilder sb = new StringBuilder();
//		if(StringUtils.isNotBlank(urls)){
//			for (String url:urls.split(regex)){
//				if(StringUtils.isBlank(url)){
//					continue;
//				}
//				//处理上传文件
//				Attachment Attachment = this.loadByUrl(url);
//				if(Attachment !=null){
//					//取消临时文件标记
//					if(Attachment.isTemp()){
//						Attachment.setTemp(false);
//						this.update(Attachment);
//					}
//					if(sb.length() > 0){
//						sb.append(regex);
//					}
//					sb.append(url);
//				}
//			}
//		}
//		return sb.toString();
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public String checkTemp(String urls) {
//		StringBuilder sb = new StringBuilder();
//		if(StringUtils.isNotBlank(urls)){
//			for (String url:urls.split(regex)){
//				if(StringUtils.isBlank(url)){
//					continue;
//				}
//				//处理上传文件
//				Attachment Attachment = this.loadByUrl(url);
//				if(Attachment !=null){
//					//切换临时文件标记
//					if(!Attachment.isTemp()){
//						Attachment.setTemp(true);
//						this.update(Attachment);
//					}
//					if(sb.length() > 0){
//						sb.append(regex);
//					}
//					sb.append(url);
//				}
//			}
//		}
//		return sb.toString();
//	}
//
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public String checkUpdate(String oldUrls, String newUrls) {
//		if(StringUtils.isBlank(oldUrls) && StringUtils.isBlank(newUrls)){
//			//无任何操作，直接返回
//			return null;
//		}
//		if(StringUtils.isBlank(newUrls)){
//			//新文件为空，删除旧文件
//			this.deleteByUrl(oldUrls);
//			return null;
//		}
//		if(StringUtils.isBlank(oldUrls)){
//			//添加新文件
//			return this.checkAdd(newUrls);
//		}
//		if(oldUrls.equals(newUrls)){
//			//无任何操作，直接返回
//			return oldUrls;
//		}else {
//			//有变化
//			String urls = this.checkAdd(newUrls);
//			for(String url : oldUrls.split(regex)) {
//				if (StringUtils.isBlank(url)) {
//					continue;
//				}
//				if(!urls.contains(url)) {
//					AttachmentDao.delete(getEqCriteria("url",url));
//				}
//			}
//			return urls;
//
//		}
//	}
//}