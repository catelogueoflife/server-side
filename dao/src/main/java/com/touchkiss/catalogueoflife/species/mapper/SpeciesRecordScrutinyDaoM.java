package com.touchkiss.catalogueoflife.species.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesRecordScrutiny;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Mapper
public interface SpeciesRecordScrutinyDaoM {
    int insert(SpeciesRecordScrutiny speciesRecordScrutiny);

    int insertWithoutId(SpeciesRecordScrutiny speciesRecordScrutiny);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("speciesRecordScrutiny") SpeciesRecordScrutiny speciesRecordScrutiny, @Param("id") Integer id);

    int updateSelectiveById(@Param("speciesRecordScrutiny") SpeciesRecordScrutiny speciesRecordScrutiny, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("speciesRecordScrutiny") SpeciesRecordScrutiny speciesRecordScrutiny, @Param("wheres") Map<String, String> wheres);

    SpeciesRecordScrutiny selectById(Integer id);

    List<SpeciesRecordScrutiny> querySpeciesRecordScrutinyList(@Param("wheres") Map<String, String> wheres);

    Page<SpeciesRecordScrutiny> findByPage(@Param("wheres") Map<String, String> wheres);
}
