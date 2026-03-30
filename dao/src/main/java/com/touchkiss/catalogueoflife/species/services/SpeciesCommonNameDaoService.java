package com.touchkiss.catalogueoflife.species.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesCommonName;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
public interface SpeciesCommonNameDaoService {
    boolean insert(SpeciesCommonName speciesCommonName);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(SpeciesCommonName speciesCommonName, Integer id);

    boolean updateSelectiveById(SpeciesCommonName speciesCommonName, Integer id);

    int updateSelectiveByMap(SpeciesCommonName speciesCommonName, Map<String, String> wheres);

    SpeciesCommonName selectById(Integer id);

    List<SpeciesCommonName> querySpeciesCommonNameList(Map<String, String> map);

    Page<SpeciesCommonName> findByPage(int pageNo, int pageSize, Map<String, String> map);
}
