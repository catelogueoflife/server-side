package com.touchkiss.catalogueoflife.life.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.life.bean.LifeCategorySourceDatabase;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-19 13:41:20
 *
 * @author Touchkiss
 */
public interface LifeCategorySourceDatabaseDaoService {
    boolean insert(LifeCategorySourceDatabase lifeCategorySourceDatabase);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(LifeCategorySourceDatabase lifeCategorySourceDatabase, Integer id);

    boolean updateSelectiveById(LifeCategorySourceDatabase lifeCategorySourceDatabase, Integer id);

    int updateSelectiveByMap(LifeCategorySourceDatabase lifeCategorySourceDatabase, Map<String, String> wheres);

    LifeCategorySourceDatabase selectById(Integer id);

    List<LifeCategorySourceDatabase> queryLifeCategorySourceDatabaseList(Map<String, String> map);

    Page<LifeCategorySourceDatabase> findByPage(int pageNo, int pageSize, Map<String, String> map);
}
