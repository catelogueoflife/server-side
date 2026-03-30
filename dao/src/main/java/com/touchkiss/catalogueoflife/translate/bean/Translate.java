package com.touchkiss.catalogueoflife.translate.bean;

import lombok.Data;

/**
 * Created on 2020-03-21 17:02:51
 *
 * @author Touchkiss
 */
@Data
public class Translate {
    private Integer id; //
    private String canonicalName; //权威名称
    private String translate; //翻译
    private String fullName; //全名
    private String language; //语言
    private String lid; //
    private String treeId; //
    private Boolean fetched; //是否经过爬取
    private Long createTime; //
    private Long lastModifyTime; //
}
