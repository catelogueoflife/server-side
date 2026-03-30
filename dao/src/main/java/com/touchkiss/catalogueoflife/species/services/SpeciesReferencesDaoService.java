package com.touchkiss.catalogueoflife.species.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesReferences;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
public interface SpeciesReferencesDaoService {
    boolean insert(SpeciesReferences speciesReferences);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(SpeciesReferences speciesReferences, Integer id);

    boolean updateSelectiveById(SpeciesReferences speciesReferences, Integer id);

    int updateSelectiveByMap(SpeciesReferences speciesReferences, Map<String, String> wheres);

    SpeciesReferences selectById(Integer id);

    List<SpeciesReferences> querySpeciesReferencesList(Map<String, String> map);

    Page<SpeciesReferences> findByPage(int pageNo, int pageSize, Map<String, String> map);
}
