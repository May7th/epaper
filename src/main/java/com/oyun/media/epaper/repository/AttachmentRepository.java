//package com.oyun.media.epaper.repository;
//
//import com.oyun.media.epaper.domain.Attachment;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.jpa.repository.JpaRepository;
//
///**
// * @program: epaper
// * @description: 附件
// * @author: changzhen
// * @create: 2018-06-04 11:42
// **/
//public interface AttachmentRepository extends JpaRepository<Attachment,Long>{
//
//    /**
//     * 通过Id删除附件
//     * @param id
//     */
//    @Override
//    void deleteById(Long id);
//
//    Attachment findByUrl(String url);
//
//    Page<Attachment> findAllByTemp(boolean temp);
//}
