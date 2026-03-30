package com.touchkiss.catalogueoflife.spider.base;

/**
 * Created on 2020/03/18 17:10
 *
 * @author Touchkiss
 */
public interface BaseConvertor<T, R> {
    T convert(R r);
}
