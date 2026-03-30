package com.touchkiss.catalogueoflife.species.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesReferences;
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
public interface SpeciesReferencesDaoM {
    int insert(SpeciesReferences speciesReferences);

    int insertWithoutId(SpeciesReferences speciesReferences);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("speciesReferences") SpeciesReferences speciesReferences, @Param("id") Integer id);

    int updateSelectiveById(@Param("speciesReferences") SpeciesReferences speciesReferences, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("speciesReferences") SpeciesReferences speciesReferences, @Param("wheres") Map<String, String> wheres);

    SpeciesReferences selectById(Integer id);

    List<SpeciesReferences> querySpeciesReferencesList(@Param("wheres") Map<String, String> wheres);

    Page<SpeciesReferences> findByPage(@Param("wheres") Map<String, String> wheres);
}
