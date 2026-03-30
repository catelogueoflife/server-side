package com.touchkiss.catalogueoflife.translate.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.touchkiss.catalogueoflife.translate.bean.Translate;
import com.touchkiss.catalogueoflife.translate.mapper.TranslateDaoM;
import com.touchkiss.catalogueoflife.translate.services.TranslateDaoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-21 17:02:51
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class TranslateDaoServiceImpl implements TranslateDaoService {
    @Resource
    TranslateDaoM translateDaoM;

    @Override
    public boolean insert(Translate translate) {
        long currentTimeMillis = System.currentTimeMillis();
        if (translate.getCreateTime() == null) {
            translate.setCreateTime(currentTimeMillis);
        }
        if (translate.getLastModifyTime() == null) {
            translate.setLastModifyTime(currentTimeMillis);
        }
        if (translate.getId() == null) {
            return translateDaoM.insertWithoutId(translate) == 1;
        }
        return translateDaoM.insert(translate) == 1;
    }

    @Override
    public boolean deleteById(Integer id) {
        return translateDaoM.deleteById(id) == 1;
    }

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return translateDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(Translate translate, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        translate.setLastModifyTime(currentTimeMillis);
        return translateDaoM.updateById(translate, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(Translate translate, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        translate.setLastModifyTime(currentTimeMillis);
        return translateDaoM.updateSelectiveById(translate, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(Translate translate, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        translate.setLastModifyTime(currentTimeMillis);
        return translateDaoM.updateSelectiveByMap(translate, wheres);
    }

    @Override
    public Translate selectById(Integer id) {
        return translateDaoM.selectById(id);
    }

    @Override
    public List<Translate> queryTranslateList(Map<String, String> map) {
        return translateDaoM.queryTranslateList(null, map);
    }

    @Override
    public Page<Translate> findByPage(int pageNo, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNo, pageSize);
        return translateDaoM.findByPage(null, map);
    }
}
