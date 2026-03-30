package com.touchkiss.catalogueoflife.spider.translate.services;

/**
 * 生物翻译爬虫服务接口（biodinfo.org）
 *
 * @author Touchkiss
 */
public interface BiodinfoTranslateSpiderService {
    /**
     * 抓取指定节点的翻译数据
     *
     * @param id     叶节点 id，为空时全量拉取 treeid 下所有翻译
     * @param treeid 分类树 id
     * @return 抓取是否成功
     */
    boolean fetchTranslate(String id, String treeid);

    void autoFetch();
}
