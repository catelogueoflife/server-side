package com.touchkiss.catalogueoflife.species.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesCommonNameReferences;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
public interface SpeciesCommonNameReferencesDaoService {
    boolean insert(SpeciesCommonNameReferences speciesCommonNameReferences);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(SpeciesCommonNameReferences speciesCommonNameReferences, Integer id);

    boolean updateSelectiveById(SpeciesCommonNameReferences speciesCommonNameReferences, Integer id);

    int updateSelectiveByMap(SpeciesCommonNameReferences speciesCommonNameReferences, Map<String, String> wheres);

    SpeciesCommonNameReferences selectById(Integer id);

    List<SpeciesCommonNameReferences> querySpeciesCommonNameReferencesList(Map<String, String> map);

    Page<SpeciesCommonNameReferences> findByPage(int pageNo, int pageSize, Map<String, String> map);
}
