package com.touchkiss.catalogueoflife.life.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.life.bean.LifeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-19 13:41:20
 *
 * @author Touchkiss
 */
@Mapper
public interface LifeCategoryDaoM {
    int insert(LifeCategory lifeCategory);

    int insertWithoutId(LifeCategory lifeCategory);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("lifeCategory") LifeCategory lifeCategory, @Param("id") Integer id);

    int updateSelectiveById(@Param("lifeCategory") LifeCategory lifeCategory, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("lifeCategory") LifeCategory lifeCategory, @Param("wheres") Map<String, String> wheres);

    LifeCategory selectById(Integer id);

    List<LifeCategory> queryLifeCategoryList(@Param("wheres") Map<String, Object> wheres);

    Page<LifeCategory> findByPage(@Param("wheres") Map<String, String> wheres);

    String queryTreeId(Integer id);

    List<LifeCategory> queryTree(@Param("ids") List<Integer> ids);
}
