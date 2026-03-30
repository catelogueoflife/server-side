package com.touchkiss.catalogueoflife.spider.species.services;

/**
 * 物种爬虫服务接口
 *
 * @author Touchkiss
 */
public interface SpeciesSpiderService {
    /**
     * 按物种 id 抓取详情
     *
     * @param id 32位 hex 物种 ID
     * @return 抓取是否成功
     */
    boolean fetchSpecies(String id);

    void autoFetch();
}
