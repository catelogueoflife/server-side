package com.touchkiss.catelogueoflife.species.services.impl;

import com.touchkiss.catelogueoflife.species.bean.SpeciesSynonyms;
import com.touchkiss.catelogueoflife.species.mapper.SpeciesSynonymsDaoM;
import com.touchkiss.catelogueoflife.species.services.SpeciesSynonymsDaoService;
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
public class SpeciesSynonymsDaoServiceImpl implements SpeciesSynonymsDaoService {
	@Resource
	SpeciesSynonymsDaoM speciesSynonymsDaoM;

	@Override
	public boolean insert(SpeciesSynonyms speciesSynonyms) {
        long currentTimeMillis = System.currentTimeMillis();
        if (speciesSynonyms.getCreateTime() == null) {
            speciesSynonyms.setCreateTime(currentTimeMillis);
        }
        if (speciesSynonyms.getLastModifyTime() == null) {
            speciesSynonyms.setLastModifyTime(currentTimeMillis);
        }
        if (speciesSynonyms.getId() == null) {
            return speciesSynonymsDaoM.insertWithoutId(speciesSynonyms) == 1;
        }
        return speciesSynonymsDaoM.insert(speciesSynonyms) == 1;
	}

	@Override
	public boolean deleteById(Integer id) {
        return speciesSynonymsDaoM.deleteById(id) == 1;
	}

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return speciesSynonymsDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(SpeciesSynonyms speciesSynonyms, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesSynonyms.setLastModifyTime(currentTimeMillis);
        return speciesSynonymsDaoM.updateById(speciesSynonyms, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(SpeciesSynonyms speciesSynonyms, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesSynonyms.setLastModifyTime(currentTimeMillis);
        return speciesSynonymsDaoM.updateSelectiveById(speciesSynonyms, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(SpeciesSynonyms speciesSynonyms, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        speciesSynonyms.setLastModifyTime(currentTimeMillis);
        return speciesSynonymsDaoM.updateSelectiveByMap(speciesSynonyms, wheres);
    }

    @Override
    public SpeciesSynonyms selectById(Integer id) {
        return speciesSynonymsDaoM.selectById(id);
    }

	@Override
	public List<SpeciesSynonyms> querySpeciesSynonymsList(Map<String, String> map) {
		return speciesSynonymsDaoM.querySpeciesSynonymsList(map);
	}

	@Override
	public Page<SpeciesSynonyms> findByPage(int pageNo, int pageSize, Map<String, String> map) {
		PageHelper.startPage(pageNo, pageSize);
		return speciesSynonymsDaoM.findByPage(map);
	}
}
