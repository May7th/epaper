package com.oyun.media.epaper.datatransfer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.bson.Document;

import java.io.IOException;


public class MongoUtilTest {
	public static MongoUtil instance = MongoUtil.instance;

	public static void readTest(){
		String dbName = "dno";
        String collName = "dp";
        String picDBName = "picsdb";
        MongoCollection<Document> coll = MongoUtilTest.instance.getCollection(dbName, collName);
        DB picDB = MongoUtilTest.instance.getDBDB(picDBName);
		MongoCursor<Document> sd =coll.find().iterator();
		GridFS gfsPhoto = new GridFS(picDB);
		try {
			int index = 0;
			while(sd.hasNext()){
				if(index >= 3){
					break;
				}
				index++;
				Document doc = sd.next();
				DR dr = new DR();
				System.out.println(dr);
				// 保存图片
				// 1 保存大图 因为原始图会较大，这个图是原始图降低分辨率之后的图
				DBObject query  = new BasicDBObject("filename", dr.getId());
				GridFSDBFile gf = gfsPhoto.findOne(query);
				if(gf != null){
					gf.writeTo("F:/temp/"+dr.getId()+"_"+dr.getName());
				}
				// 2 保存可能存在的原始高清图
				DBObject query2  = new BasicDBObject("filename", dr.getId()+"raw");
				GridFSDBFile rgf = gfsPhoto.findOne(query2);
				if(rgf != null){
					gf.writeTo("F:/temp/"+dr.getId()+"_raw_"+dr.getName());
				}
				// 3 保存子图
//				for(SubDR sdr:dr.getList()){
//					DBObject query3  = new BasicDBObject("filename", dr.getId());
//					GridFSDBFile sgf = gfsPhoto.findOne(query3);
//					if(sgf != null){
//						sgf.writeTo("F:/temp/"+sdr.getId()+"_"+sdr.getName());
//					}
//				}
			}
			MongoUtilTest.instance.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * 测试入口
     *
     * @param args
     * @throws CloneNotSupportedException
     */
    public static void main(String[] args) {
        readTest();

    }

}
