package com.touchkiss.catalogueoflife.spider.species2000cn.bean;

import lombok.Data;

/**
 * Created on 2020/07/07 16:22
 *
 * @author Touchkiss
 */
@Data
public class TreeItemResponse {
    private String id;
    private String cnName;
    private String enName;
    private String className;
    private boolean isParent;
    private String pinyin;
    private String url;
}
