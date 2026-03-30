package com.touchkiss.catalogueoflife.species.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesCommonName;
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
public interface SpeciesCommonNameDaoM {
    int insert(SpeciesCommonName speciesCommonName);

    int insertWithoutId(SpeciesCommonName speciesCommonName);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("speciesCommonName") SpeciesCommonName speciesCommonName, @Param("id") Integer id);

    int updateSelectiveById(@Param("speciesCommonName") SpeciesCommonName speciesCommonName, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("speciesCommonName") SpeciesCommonName speciesCommonName, @Param("wheres") Map<String, String> wheres);

    SpeciesCommonName selectById(Integer id);

    List<SpeciesCommonName> querySpeciesCommonNameList(@Param("wheres") Map<String, String> wheres);

    Page<SpeciesCommonName> findByPage(@Param("wheres") Map<String, String> wheres);
}
