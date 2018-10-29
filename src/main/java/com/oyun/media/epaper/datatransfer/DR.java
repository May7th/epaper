package com.oyun.media.epaper.datatransfer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


/**
 * 日报
 * @author stude
 *
 */
@Document(collection = "dp")
@Data
public class DR {

	@Id
	private String _id;
	private String id;
	private String name;
    /**蒙古文文本*/
	private String content;
	/**缩略后的图像文件，其大小适合在网页展示*/
	private String picFN;
	/**原始图像文件*/
	private String rawPicFN;
	private int width;
	private int height;
	@DBRef
	private ArrayList<SubPic> subPics;

	
}
