package com.touchkiss.catelogueoflife.species.services.impl;

import com.touchkiss.catelogueoflife.species.bean.SpeciesCommonName;
import com.touchkiss.catelogueoflife.species.mapper.SpeciesCommonNameDaoM;
import com.touchkiss.catelogueoflife.species.services.SpeciesCommonNameDaoService;
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
public class SpeciesCommonNameDaoServiceImpl implements SpeciesCommonNameDaoService {
	@Resource
	SpeciesCommonNameDaoM speciesCommonNameDaoM;

	@Override
	public boolean insert(SpeciesCommonName speciesCommonName) {
        long currentTimeMillis = System.currentTimeMillis();
        if (speciesCommonName.getCreateTime() == null) {
            speciesCommonName.setCreateTime(currentTimeMillis);
        }
        if (speciesCommonName.getLastModifyTime() == null) {
            speciesCommonName.setLastModifyTime(currentTimeMillis);
        }
        if (speciesCommonName.getId() == null) {
            return speciesCommonNameDaoM.insertWithoutId(speciesCommonName) == 1;
        }
        return speciesCommonNameDaoM.insert(speciesCommonName) == 1;
	}

	@Override
	public boolean deleteById(Integer id) {
        return speciesCommonNameDaoM.deleteById(id) == 1;
	}

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return speciesCommonNameDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(SpeciesCommonName speciesCommonName, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesCommonName.setLastModifyTime(currentTimeMillis);
        return speciesCommonNameDaoM.updateById(speciesCommonName, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(SpeciesCommonName speciesCommonName, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesCommonName.setLastModifyTime(currentTimeMillis);
        return speciesCommonNameDaoM.updateSelectiveById(speciesCommonName, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(SpeciesCommonName speciesCommonName, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesCommonName.setLastModifyTime(currentTimeMillis);
        return speciesCommonNameDaoM.updateSelectiveByMap(speciesCommonName, wheres);
    }

    @Override
    public SpeciesCommonName selectById(Integer id) {
        return speciesCommonNameDaoM.selectById(id);
    }

	@Override
	public List<SpeciesCommonName> querySpeciesCommonNameList(Map<String, String> map) {
		return speciesCommonNameDaoM.querySpeciesCommonNameList(map);
	}

	@Override
	public Page<SpeciesCommonName> findByPage(int pageNo, int pageSize, Map<String, String> map) {
		PageHelper.startPage(pageNo, pageSize);
		return speciesCommonNameDaoM.findByPage(map);
	}
}
