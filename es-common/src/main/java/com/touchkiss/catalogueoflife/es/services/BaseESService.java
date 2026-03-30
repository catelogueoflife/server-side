package com.touchkiss.catalogueoflife.es.services;

import com.touchkiss.catalogueoflife.es.bean.ESPage;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020/04/09 23:40
 *
 * @author Touchkiss
 */
public interface BaseESService<T> {
    /**
     * 新增或更新一条记录
     *
     * @param entity
     * @return
     */
    boolean saveOrUpdate(T entity);

    /**
     * 根据id删除一条记录
     *
     * @param ids
     * @return
     */
    boolean delete(Object... ids);

    /**
     * 根据id获取一条记录
     *
     * @param id
     * @return
     */
    T getOne(Object id);

    /**
     * 根据id数组获取多条记录
     *
     * @param fields
     * @param ids
     * @return
     */
    List<T> getMulti(String[] fields, Object... ids);

    /**
     * 根据条件查询page
     *
     * @param fields
     * @param must
     * @param should
     * @param must_not
     * @param pageNo
     * @param pageSize
     * @return
     */
    ESPage<T> search(String[] fields, Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not, int pageNo, int pageSize);

    /**
     * 根据条件查询count
     *
     * @param must
     * @param should
     * @param must_not
     * @return
     */
    long count(Map<String, Object> must, Map<String, Object> should, Map<String, Object> must_not);

    /**
     * 按字段部分更新
     *
     * @param updates
     * @param ids
     * @return
     */
    boolean batchUpdate(Map<String, Object> updates, Object... ids);
}
