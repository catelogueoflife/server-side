package com.touchkiss.catalogueoflife.spider.constants;

/**
 * Created on 2020/04/02 17:39
 *
 * @author Touchkiss
 */
public class RedisConstants {
    //    未抓取wiki词条
    public final static String WIKI_QUERY_NOT_FETCHED = "WIKI_QUERY_NOT_FETCHED";
    //    已抓取wiki词条
    public final static String WIKI_QUERY_HAS_FETCHED = "WIKI_QUERY_HAS_FETCHED";

    //未抓取百度百科地址
    public final static String BAIKE_NOT_FETCHED_URLS = "BAIKE_NOT_FETCHED_URLS";
    //已抓取百度百科地址
    public final static String BAIKE_HAS_FETCHED_URLS = "BAIKE_HAS_FETCHED_URLS";
    //百科已经存储到ES
    public final static String BAIKE_HAS_STORED_TO_ES = "BAIKE_HAS_STORED_TO_ES";
    public final static String BAIKE_NOT_STORED_TO_ES = "BAIKE_NOT_STORED_TO_ES";


}
