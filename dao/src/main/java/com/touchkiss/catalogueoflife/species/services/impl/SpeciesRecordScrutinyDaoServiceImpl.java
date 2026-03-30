package com.touchkiss.catalogueoflife.species.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.touchkiss.catalogueoflife.species.bean.SpeciesRecordScrutiny;
import com.touchkiss.catalogueoflife.species.mapper.SpeciesRecordScrutinyDaoM;
import com.touchkiss.catalogueoflife.species.services.SpeciesRecordScrutinyDaoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class SpeciesRecordScrutinyDaoServiceImpl implements SpeciesRecordScrutinyDaoService {
    @Resource
    SpeciesRecordScrutinyDaoM speciesRecordScrutinyDaoM;

    @Override
    public boolean insert(SpeciesRecordScrutiny speciesRecordScrutiny) {
        long currentTimeMillis = System.currentTimeMillis();
        if (speciesRecordScrutiny.getCreateTime() == null) {
            speciesRecordScrutiny.setCreateTime(currentTimeMillis);
        }
        if (speciesRecordScrutiny.getLastModifyTime() == null) {
            speciesRecordScrutiny.setLastModifyTime(currentTimeMillis);
        }
        if (speciesRecordScrutiny.getId() == null) {
            return speciesRecordScrutinyDaoM.insertWithoutId(speciesRecordScrutiny) == 1;
        }
        return speciesRecordScrutinyDaoM.insert(speciesRecordScrutiny) == 1;
    }

    @Override
    public boolean deleteById(Integer id) {
        return speciesRecordScrutinyDaoM.deleteById(id) == 1;
    }

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return speciesRecordScrutinyDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(SpeciesRecordScrutiny speciesRecordScrutiny, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesRecordScrutiny.setLastModifyTime(currentTimeMillis);
        return speciesRecordScrutinyDaoM.updateById(speciesRecordScrutiny, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(SpeciesRecordScrutiny speciesRecordScrutiny, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesRecordScrutiny.setLastModifyTime(currentTimeMillis);
        return speciesRecordScrutinyDaoM.updateSelectiveById(speciesRecordScrutiny, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(SpeciesRecordScrutiny speciesRecordScrutiny, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesRecordScrutiny.setLastModifyTime(currentTimeMillis);
        return speciesRecordScrutinyDaoM.updateSelectiveByMap(speciesRecordScrutiny, wheres);
    }

    @Override
    public SpeciesRecordScrutiny selectById(Integer id) {
        return speciesRecordScrutinyDaoM.selectById(id);
    }

    @Override
    public List<SpeciesRecordScrutiny> querySpeciesRecordScrutinyList(Map<String, String> map) {
        return speciesRecordScrutinyDaoM.querySpeciesRecordScrutinyList(map);
    }

    @Override
    public Page<SpeciesRecordScrutiny> findByPage(int pageNo, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNo, pageSize);
        return speciesRecordScrutinyDaoM.findByPage(map);
    }
}
