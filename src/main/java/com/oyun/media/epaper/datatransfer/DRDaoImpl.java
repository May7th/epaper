package com.oyun.media.epaper.datatransfer;

import com.oyun.media.epaper.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: data-transfer
 * @description:
 * @author: changzhen
 * @create: 2018-10-24 22:01
 **/
@Service
public class DRDaoImpl implements DRDao {


    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private IPaperService paperService;

    @Override
    public void saveDemo(DR dr) {

    }

    @Override
    public void removeDemo(Long id) {

    }

    @Override
    public void updateDemo(DR dr) {

    }

    @Override
    public List<DR> findAll(){
        List<DR> drList = mongoTemplate.findAll(DR.class);
        return drList;
    }

    @Override
    public DR findDemoById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        DR dr = mongoTemplate.findOne(query, DR.class);

//        DB picDB = MongoUtilTest.instance.getDBDB("picsdb");
//        GridFS gfsPhoto = new GridFS(picDB);
////        GridFSFindIterable iterable = gridFsTemplate.find(query(whereFilename().is(dr.getName())));
//        DBObject query2  = new BasicDBObject("filename", dr.getId());
//        GridFSDBFile gf = gfsPhoto.findOne(query2);
//        if(gf != null){
//            try {
//                gf.writeTo("/Users/changzhen/work/oyun/epaper/epaper/temp/"+dr.getId()+"_"+dr.getName());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        MongoCursor mongoCursor= iterable.iterator();
//        List results = new ArrayList();
//        while(mongoCursor.hasNext()){
//            results.add(mongoCursor.next());
//        }
//        GridFSDBFile gridFSDBFile = (GridFSDBFile) results.get(0);
//        try {
//            gridFSDBFile.writeTo(new File("/Users/changzhen/work/oyun/epaper/epaper/temp/" + gridFSDBFile.getFilename()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return dr;
    }


}
