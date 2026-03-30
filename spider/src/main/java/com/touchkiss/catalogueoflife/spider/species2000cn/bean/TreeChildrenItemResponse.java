package com.touchkiss.catalogueoflife.spider.species2000cn.bean;

import lombok.Data;

/**
 * Created on 2020/07/07 16:17
 *
 * @author Touchkiss
 */
@Data
public class TreeChildrenItemResponse {
    private String id;
    private String name;
    private boolean isParent;
    private String pinyin;
    private String url;
}
