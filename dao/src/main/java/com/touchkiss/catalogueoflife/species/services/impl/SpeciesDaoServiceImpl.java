package com.touchkiss.catalogueoflife.species.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.touchkiss.catalogueoflife.species.bean.Species;
import com.touchkiss.catalogueoflife.species.mapper.SpeciesDaoM;
import com.touchkiss.catalogueoflife.species.services.SpeciesDaoService;
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
public class SpeciesDaoServiceImpl implements SpeciesDaoService {
    @Resource
    SpeciesDaoM speciesDaoM;

    @Override
    public boolean insert(Species species) {
        long currentTimeMillis = System.currentTimeMillis();
        if (species.getCreateTime() == null) {
            species.setCreateTime(currentTimeMillis);
        }
        if (species.getLastModifyTime() == null) {
            species.setLastModifyTime(currentTimeMillis);
        }
        return speciesDaoM.insert(species) == 1;
    }

    @Override
    public boolean deleteById(String id) {
        return speciesDaoM.deleteById(id) == 1;
    }

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return speciesDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(Species species, String id) {
        long currentTimeMillis = System.currentTimeMillis();
        species.setLastModifyTime(currentTimeMillis);
        return speciesDaoM.updateById(species, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(Species species, String id) {
        long currentTimeMillis = System.currentTimeMillis();
        species.setLastModifyTime(currentTimeMillis);
        return speciesDaoM.updateSelectiveById(species, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(Species species, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        species.setLastModifyTime(currentTimeMillis);
        return speciesDaoM.updateSelectiveByMap(species, wheres);
    }

    @Override
    public Species selectById(String id) {
        return speciesDaoM.selectById(id);
    }

    @Override
    public List<Species> querySpeciesList(Map<String, String> map) {
        return speciesDaoM.querySpeciesList(map);
    }

    @Override
    public Page<Species> findByPage(int pageNo, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNo, pageSize);
        return speciesDaoM.findByPage(map);
    }
}
