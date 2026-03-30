package com.touchkiss.catalogueoflife.life.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.life.bean.SourceDatabase;
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
public interface SourceDatabaseDaoM {
    int insert(SourceDatabase sourceDatabase);

    int insertWithoutId(SourceDatabase sourceDatabase);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("sourceDatabase") SourceDatabase sourceDatabase, @Param("id") Integer id);

    int updateSelectiveById(@Param("sourceDatabase") SourceDatabase sourceDatabase, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("sourceDatabase") SourceDatabase sourceDatabase, @Param("wheres") Map<String, String> wheres);

    SourceDatabase selectById(Integer id);

    List<SourceDatabase> querySourceDatabaseList(@Param("wheres") Map<String, String> wheres);

    Page<SourceDatabase> findByPage(@Param("wheres") Map<String, String> wheres);
}
