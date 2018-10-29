package com.oyun.media.epaper.datatransfer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subPic")
@Data
public class SubPic {

	@Id
	private String _id;
	private String id;
	private String name;
	private String content;
	private String picFN;
	private int width;
	private int height;
	private int x;
	private int y;
}