package com.touchkiss.catalogueoflife.species.mapper;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesSynonyms;
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
public interface SpeciesSynonymsDaoM {
    int insert(SpeciesSynonyms speciesSynonyms);

    int insertWithoutId(SpeciesSynonyms speciesSynonyms);

    int deleteById(Integer id);

    int deleteByMap(@Param("wheres") Map<String, String> wheres);

    int updateById(@Param("speciesSynonyms") SpeciesSynonyms speciesSynonyms, @Param("id") Integer id);

    int updateSelectiveById(@Param("speciesSynonyms") SpeciesSynonyms speciesSynonyms, @Param("id") Integer id);

    int updateSelectiveByMap(@Param("speciesSynonyms") SpeciesSynonyms speciesSynonyms, @Param("wheres") Map<String, String> wheres);

    SpeciesSynonyms selectById(Integer id);

    List<SpeciesSynonyms> querySpeciesSynonymsList(@Param("wheres") Map<String, String> wheres);

    Page<SpeciesSynonyms> findByPage(@Param("wheres") Map<String, String> wheres);
}
