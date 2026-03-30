package com.touchkiss.catalogueoflife.spider.wikipedia.services;

/**
 * Created on 2020/04/02 17:11
 *
 * @author Touchkiss
 */
public interface WikiService {
    void fetchContent(String title);

    void autoFetch();
}
