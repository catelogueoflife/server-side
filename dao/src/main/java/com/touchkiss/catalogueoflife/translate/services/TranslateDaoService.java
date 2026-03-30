package com.touchkiss.catalogueoflife.translate.services;

import com.github.pagehelper.Page;
import com.touchkiss.catalogueoflife.translate.bean.Translate;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-21 17:02:51
 *
 * @author Touchkiss
 */
public interface TranslateDaoService {
    boolean insert(Translate translate);

    boolean deleteById(Integer id);

    int deleteByMap(Map<String, String> wheres);

    boolean updateById(Translate translate, Integer id);

    boolean updateSelectiveById(Translate translate, Integer id);

    int updateSelectiveByMap(Translate translate, Map<String, String> wheres);

    Translate selectById(Integer id);

    List<Translate> queryTranslateList(Map<String, String> map);

    Page<Translate> findByPage(int pageNo, int pageSize, Map<String, String> map);
}
