package com.touchkiss.catalogueoflife.species.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.species.bean.SpeciesSynonyms;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
public interface SpeciesSynonymsDaoService {
    boolean insert(SpeciesSynonyms speciesSynonyms);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(SpeciesSynonyms speciesSynonyms, Integer id);

    boolean updateSelectiveById(SpeciesSynonyms speciesSynonyms, Integer id);

    int updateSelectiveByMap(SpeciesSynonyms speciesSynonyms, Map<String, String> wheres);

    SpeciesSynonyms selectById(Integer id);

    List<SpeciesSynonyms> querySpeciesSynonymsList(Map<String, String> map);

    Page<SpeciesSynonyms> findByPage(int pageNo, int pageSize, Map<String, String> map);
}
