package com.touchkiss.catalogueoflife.spider.life.services;

/**
 * 生命分类爬虫服务接口
 *
 * @author Touchkiss
 */
public interface LifeCategorySpiderService {
    /**
     * 按节点 id 抓取生命分类树
     *
     * @param id 节点 id
     * @return 1 表示插入, 0 表示跳过
     */
    int fetchLifeCategoryFromWeb(int id);

    void autoFetch();
}
