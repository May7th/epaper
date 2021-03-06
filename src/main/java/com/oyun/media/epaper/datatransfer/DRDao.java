package com.oyun.media.epaper.datatransfer;

import java.util.List;

/**
 * @program: data-transfer
 * @description:
 * @author: changzhen
 * @create: 2018-10-24 22:00
 **/
public interface DRDao {
    void saveDemo(DR dr);

    void removeDemo(Long id);

    void updateDemo(DR dr);

    DR findDemoById(String id);

    public List<DR> findAll();

    DR findeDemoByName(String name);
}
