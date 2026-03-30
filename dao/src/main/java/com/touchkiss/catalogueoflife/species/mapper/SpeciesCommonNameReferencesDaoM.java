package com.touchkiss.catalogueoflife.species.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesCommonNameReferences;
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
public interface SpeciesCommonNameReferencesDaoM {
    int insert(SpeciesCommonNameReferences speciesCommonNameReferences);

    int insertWithoutId(SpeciesCommonNameReferences speciesCommonNameReferences);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("speciesCommonNameReferences") SpeciesCommonNameReferences speciesCommonNameReferences, @Param("id") Integer id);

    int updateSelectiveById(@Param("speciesCommonNameReferences") SpeciesCommonNameReferences speciesCommonNameReferences, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("speciesCommonNameReferences") SpeciesCommonNameReferences speciesCommonNameReferences, @Param("wheres") Map<String, String> wheres);

    SpeciesCommonNameReferences selectById(Integer id);

    List<SpeciesCommonNameReferences> querySpeciesCommonNameReferencesList(@Param("wheres") Map<String, String> wheres);

    Page<SpeciesCommonNameReferences> findByPage(@Param("wheres") Map<String, String> wheres);
}
