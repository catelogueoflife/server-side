package com.touchkiss.catelogueoflife.species.services.impl;

import com.touchkiss.catelogueoflife.species.bean.SpeciesCommonNameReferences;
import com.touchkiss.catelogueoflife.species.mapper.SpeciesCommonNameReferencesDaoM;
import com.touchkiss.catelogueoflife.species.services.SpeciesCommonNameReferencesDaoService;
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
public class SpeciesCommonNameReferencesDaoServiceImpl implements SpeciesCommonNameReferencesDaoService {
	@Resource
	SpeciesCommonNameReferencesDaoM speciesCommonNameReferencesDaoM;

	@Override
	public boolean insert(SpeciesCommonNameReferences speciesCommonNameReferences) {
        long currentTimeMillis = System.currentTimeMillis();
        if (speciesCommonNameReferences.getCreateTime() == null) {
            speciesCommonNameReferences.setCreateTime(currentTimeMillis);
        }
        if (speciesCommonNameReferences.getLastModifyTime() == null) {
            speciesCommonNameReferences.setLastModifyTime(currentTimeMillis);
        }
        if (speciesCommonNameReferences.getId() == null) {
            return speciesCommonNameReferencesDaoM.insertWithoutId(speciesCommonNameReferences) == 1;
        }
        return speciesCommonNameReferencesDaoM.insert(speciesCommonNameReferences) == 1;
	}

	@Override
	public boolean deleteById(Integer id) {
        return speciesCommonNameReferencesDaoM.deleteById(id) == 1;
	}

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return speciesCommonNameReferencesDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(SpeciesCommonNameReferences speciesCommonNameReferences, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesCommonNameReferences.setLastModifyTime(currentTimeMillis);
        return speciesCommonNameReferencesDaoM.updateById(speciesCommonNameReferences, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(SpeciesCommonNameReferences speciesCommonNameReferences, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesCommonNameReferences.setLastModifyTime(currentTimeMillis);
        return speciesCommonNameReferencesDaoM.updateSelectiveById(speciesCommonNameReferences, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(SpeciesCommonNameReferences speciesCommonNameReferences, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesCommonNameReferences.setLastModifyTime(currentTimeMillis);
        return speciesCommonNameReferencesDaoM.updateSelectiveByMap(speciesCommonNameReferences, wheres);
    }

    @Override
    public SpeciesCommonNameReferences selectById(Integer id) {
        return speciesCommonNameReferencesDaoM.selectById(id);
    }

	@Override
	public List<SpeciesCommonNameReferences> querySpeciesCommonNameReferencesList(Map<String, String> map) {
		return speciesCommonNameReferencesDaoM.querySpeciesCommonNameReferencesList(map);
	}

	@Override
	public Page<SpeciesCommonNameReferences> findByPage(int pageNo, int pageSize, Map<String, String> map) {
		PageHelper.startPage(pageNo, pageSize);
		return speciesCommonNameReferencesDaoM.findByPage(map);
	}
}
