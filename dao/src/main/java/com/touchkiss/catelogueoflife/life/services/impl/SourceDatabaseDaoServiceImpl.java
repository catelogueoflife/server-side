package com.touchkiss.catelogueoflife.life.services.impl;

import com.touchkiss.catelogueoflife.life.bean.SourceDatabase;
import com.touchkiss.catelogueoflife.life.mapper.SourceDatabaseDaoM;
import com.touchkiss.catelogueoflife.life.services.SourceDatabaseDaoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Map;
import java.util.List;

/**
 * Created on 2020-03-19 13:41:20
 *
 * @author Touchkiss
 */
@Service
@Transactional
public class SourceDatabaseDaoServiceImpl implements SourceDatabaseDaoService {
	@Resource
	SourceDatabaseDaoM sourceDatabaseDaoM;

	@Override
	public boolean insert(SourceDatabase sourceDatabase) {
        long currentTimeMillis = System.currentTimeMillis();
        if (sourceDatabase.getCreateTime() == null) {
            sourceDatabase.setCreateTime(currentTimeMillis);
        }
        if (sourceDatabase.getLastModifyTime() == null) {
            sourceDatabase.setLastModifyTime(currentTimeMillis);
        }
        if (sourceDatabase.getId() == null) {
            return sourceDatabaseDaoM.insertWithoutId(sourceDatabase) == 1;
        }
        return sourceDatabaseDaoM.insert(sourceDatabase) == 1;
	}

	@Override
	public boolean deleteById(Integer id) {
        return sourceDatabaseDaoM.deleteById(id) == 1;
	}

    @Override
    public int deleteByMap(Map<String, String> wheres) {
        return sourceDatabaseDaoM.deleteByMap(wheres);
    }

    @Override
    public boolean updateById(SourceDatabase sourceDatabase, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        sourceDatabase.setLastModifyTime(currentTimeMillis);
        return sourceDatabaseDaoM.updateById(sourceDatabase, id) == 1;
    }

    @Override
    public boolean updateSelectiveById(SourceDatabase sourceDatabase, Integer id) {
        long currentTimeMillis = System.currentTimeMillis();
        sourceDatabase.setLastModifyTime(currentTimeMillis);
        return sourceDatabaseDaoM.updateSelectiveById(sourceDatabase, id) == 1;
    }

    @Override
    public int updateSelectiveByMap(SourceDatabase sourceDatabase, Map<String, String> wheres) {
        long currentTimeMillis = System.currentTimeMillis();
        sourceDatabase.setLastModifyTime(currentTimeMillis);
        return sourceDatabaseDaoM.updateSelectiveByMap(sourceDatabase, wheres);
    }

    @Override
    public SourceDatabase selectById(Integer id) {
        return sourceDatabaseDaoM.selectById(id);
    }

	@Override
	public List<SourceDatabase> querySourceDatabaseList(Map<String, String> map) {
		return sourceDatabaseDaoM.querySourceDatabaseList(map);
	}

	@Override
	public Page<SourceDatabase> findByPage(int pageNo, int pageSize, Map<String, String> map) {
		PageHelper.startPage(pageNo, pageSize);
		return sourceDatabaseDaoM.findByPage(map);
	}
}
