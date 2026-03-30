package com.touchkiss.catalogueoflife.life.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.touchkiss.catalogueoflife.life.bean.LifeCategorySourceDatabase;
import com.touchkiss.catalogueoflife.life.mapper.LifeCategorySourceDatabaseDaoM;
import com.touchkiss.catalogueoflife.life.services.LifeCategorySourceDatabaseDaoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-19 13:41:20
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class LifeCategorySourceDatabaseDaoServiceImpl implements LifeCategorySourceDatabaseDaoService {
    @Resource
    LifeCategorySourceDatabaseDaoM lifeCategorySourceDatabaseDaoM;

    @Override
    public boolean insert(LifeCategorySourceDatabase lifeCategorySourceDatabase) {
        long currentTimeMillis = System.currentTimeMillis();
        if (lifeCategorySourceDatabase.getCreateTime() == null) {
            lifeCategorySourceDatabase.setCreateTime(currentTimeMillis);
        }
        if (lifeCategorySourceDatabase.getLastModifyTime() == null) {
            lifeCategorySourceDatabase.setLastModifyTime(currentTimeMillis);
        }
        if (lifeCategorySourceDatabase.getId() == null) {
            return lifeCategorySourceDatabaseDaoM.insertWithoutId(lifeCategorySourceDatabase) == 1;
        }
        return lifeCategorySourceDatabaseDaoM.insert(lifeCategorySourceDatabase) == 1;
    }

    @Override
    public boolean deleteById(Integer id) {
        return lifeCategorySourceDatabaseDaoM.deleteById(id) == 1;
    }

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return lifeCategorySourceDatabaseDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(LifeCategorySourceDatabase lifeCategorySourceDatabase, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        lifeCategorySourceDatabase.setLastModifyTime(currentTimeMillis);
        return lifeCategorySourceDatabaseDaoM.updateById(lifeCategorySourceDatabase, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(LifeCategorySourceDatabase lifeCategorySourceDatabase, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        lifeCategorySourceDatabase.setLastModifyTime(currentTimeMillis);
        return lifeCategorySourceDatabaseDaoM.updateSelectiveById(lifeCategorySourceDatabase, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(LifeCategorySourceDatabase lifeCategorySourceDatabase, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        lifeCategorySourceDatabase.setLastModifyTime(currentTimeMillis);
        return lifeCategorySourceDatabaseDaoM.updateSelectiveByMap(lifeCategorySourceDatabase, wheres);
    }

    @Override
    public LifeCategorySourceDatabase selectById(Integer id) {
        return lifeCategorySourceDatabaseDaoM.selectById(id);
    }

    @Override
    public List<LifeCategorySourceDatabase> queryLifeCategorySourceDatabaseList(Map<String, String> map) {
        return lifeCategorySourceDatabaseDaoM.queryLifeCategorySourceDatabaseList(map);
    }

    @Override
    public Page<LifeCategorySourceDatabase> findByPage(int pageNo, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNo, pageSize);
        return lifeCategorySourceDatabaseDaoM.findByPage(map);
    }
}
