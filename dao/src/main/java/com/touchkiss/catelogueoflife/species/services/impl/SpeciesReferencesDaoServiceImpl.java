package com.touchkiss.catelogueoflife.species.services.impl;

import com.touchkiss.catelogueoflife.species.bean.SpeciesReferences;
import com.touchkiss.catelogueoflife.species.mapper.SpeciesReferencesDaoM;
import com.touchkiss.catelogueoflife.species.services.SpeciesReferencesDaoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.List;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class SpeciesReferencesDaoServiceImpl implements SpeciesReferencesDaoService {
	@Resource
	SpeciesReferencesDaoM speciesReferencesDaoM;

	@Override
	public boolean insert(SpeciesReferences speciesReferences) {
        long currentTimeMillis = System.currentTimeMillis();
        if (speciesReferences.getCreateTime() == null) {
            speciesReferences.setCreateTime(currentTimeMillis);
        }
        if (speciesReferences.getLastModifyTime() == null) {
            speciesReferences.setLastModifyTime(currentTimeMillis);
        }
        if (speciesReferences.getId() == null) {
            return speciesReferencesDaoM.insertWithoutId(speciesReferences) == 1;
        }
        return speciesReferencesDaoM.insert(speciesReferences) == 1;
	}

	@Override
	public boolean deleteById(Integer id) {
        return speciesReferencesDaoM.deleteById(id) == 1;
	}

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return speciesReferencesDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(SpeciesReferences speciesReferences, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesReferences.setLastModifyTime(currentTimeMillis);
        return speciesReferencesDaoM.updateById(speciesReferences, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(SpeciesReferences speciesReferences, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesReferences.setLastModifyTime(currentTimeMillis);
        return speciesReferencesDaoM.updateSelectiveById(speciesReferences, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(SpeciesReferences speciesReferences, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesReferences.setLastModifyTime(currentTimeMillis);
        return speciesReferencesDaoM.updateSelectiveByMap(speciesReferences, wheres);
    }

    @Override
    public SpeciesReferences selectById(Integer id) {
        return speciesReferencesDaoM.selectById(id);
    }

	@Override
	public List<SpeciesReferences> querySpeciesReferencesList(Map<String, String> map) {
		return speciesReferencesDaoM.querySpeciesReferencesList(map);
	}

	@Override
	public Page<SpeciesReferences> findByPage(int pageNo, int pageSize, Map<String, String> map) {
		PageHelper.startPage(pageNo, pageSize);
		return speciesReferencesDaoM.findByPage(map);
	}
}
