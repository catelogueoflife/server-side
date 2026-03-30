package com.touchkiss.catalogueoflife.life.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.life.bean.LifeCategory;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-19 13:41:20
 *
 * @author Touchkiss
 */
public interface LifeCategoryDaoService {
    boolean insert(LifeCategory lifeCategory);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(LifeCategory lifeCategory, Integer id);

    boolean updateSelectiveById(LifeCategory lifeCategory, Integer id);

    int updateSelectiveByMap(LifeCategory lifeCategory, Map<String, String> wheres);

    LifeCategory selectById(Integer id);

    List<LifeCategory> queryLifeCategoryList(Map<String, Object> map);

    Page<LifeCategory> findByPage(int pageNo, int pageSize, Map<String, String> map);

    List<LifeCategory> queryTree(Integer id);
}
