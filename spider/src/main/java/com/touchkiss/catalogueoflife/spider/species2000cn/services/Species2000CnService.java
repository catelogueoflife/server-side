package com.touchkiss.catalogueoflife.spider.species2000cn.services;

import com.touchkiss.catalogueoflife.spider.species2000cn.bean.TreeItemResponse;

import java.util.List;

/**
 * Created on 2020/07/07 16:27
 *
 * @author Touchkiss
 */
public interface Species2000CnService {
    /**
     * 查询列表
     *
     * @param id
     * @return
     */
    List<TreeItemResponse> browse(String id);
}
