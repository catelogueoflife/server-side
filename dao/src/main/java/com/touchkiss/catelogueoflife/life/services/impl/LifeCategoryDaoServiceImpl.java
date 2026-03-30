package com.touchkiss.catelogueoflife.life.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.touchkiss.catelogueoflife.life.bean.LifeCategory;
import com.touchkiss.catelogueoflife.life.mapper.LifeCategoryDaoM;
import com.touchkiss.catelogueoflife.life.services.LifeCategoryDaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2020-03-19 13:41:20
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class LifeCategoryDaoServiceImpl implements LifeCategoryDaoService {
    @Resource
    LifeCategoryDaoM lifeCategoryDaoM;

    @Override
    public boolean insert(LifeCategory lifeCategory) {
        long currentTimeMillis = System.currentTimeMillis();
        if (lifeCategory.getCreateTime() == null) {
            lifeCategory.setCreateTime(currentTimeMillis);
        }
        if (lifeCategory.getLastModifyTime() == null) {
            lifeCategory.setLastModifyTime(currentTimeMillis);
        }
        if (lifeCategory.getId() == null) {
            return lifeCategoryDaoM.insertWithoutId(lifeCategory) == 1;
        }
        return lifeCategoryDaoM.insert(lifeCategory) == 1;
    }

    @Override
    public boolean deleteById(Integer id) {
        return lifeCategoryDaoM.deleteById(id) == 1;
    }

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return lifeCategoryDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(LifeCategory lifeCategory, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        lifeCategory.setLastModifyTime(currentTimeMillis);
        return lifeCategoryDaoM.updateById(lifeCategory, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(LifeCategory lifeCategory, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        lifeCategory.setLastModifyTime(currentTimeMillis);
        return lifeCategoryDaoM.updateSelectiveById(lifeCategory, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(LifeCategory lifeCategory, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        lifeCategory.setLastModifyTime(currentTimeMillis);
        return lifeCategoryDaoM.updateSelectiveByMap(lifeCategory, wheres);
    }

    @Override
    public LifeCategory selectById(Integer id) {
        return lifeCategoryDaoM.selectById(id);
    }

    @Override
    public List<LifeCategory> queryLifeCategoryList(Map<String, Object> map) {
        return lifeCategoryDaoM.queryLifeCategoryList(map);
    }

    @Override
    public Page<LifeCategory> findByPage(int pageNo, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNo, pageSize);
        return lifeCategoryDaoM.findByPage(map);
    }

    @Override
    public List<LifeCategory> queryTree(Integer id) {
        String ids = lifeCategoryDaoM.queryTreeId(id);
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        return lifeCategoryDaoM.queryTree(Arrays.stream(ids.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
    }
}
