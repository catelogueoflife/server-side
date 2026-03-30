package com.touchkiss.catalogueoflife.spider.constants;

/**
 * Created on 2020/03/18 17:35
 *
 * @author Touchkiss
 */
public class UrlConstants {
    /**
     * 抓取生命分类树地址
     * 来自 http://www.catalogueoflife.org/col/browse/tree
     */
//    public final static String LIFE_CATEGORY_TREE_FETCH_URL = "http://www.catalogueoflife.org/col/browse/tree/fetch/taxa?id=";
    /**
     * 年度生命分类树地址
     */
    public final static String LIFE_CATEGORY_TREE_FETCH_URL = "http://www.catalogueoflife.org/annual-checklist/2019/browse/tree/fetch/taxa?id=";
    /**
     * 抓取物种详细信息地址
     */
    public final static String SPECIES_FETCH_URL = "http://webservice.catalogueoflife.org/col/webservice?format=json&response=full&id=";
    /**
     * 抓取物种名称翻译
     * 来自http://ttt.biodinfo.org/
     */
    public static final String BIODINFO_FETCH_URL = "http://ttt.biodinfo.org/loadTaxa.do";

    /**
     * 维基百科按标题抓取内容
     */
    public static final String WIKI_QUERY_URL = "https://species.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&formatversion=2&titles=";

}
