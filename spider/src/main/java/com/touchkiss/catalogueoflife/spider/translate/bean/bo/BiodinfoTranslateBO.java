package com.touchkiss.catalogueoflife.spider.translate.bean.bo;

import lombok.Data;

/**
 * Created on 2020/03/21 17:07
 *
 * @author Touchkiss
 */
@Data
public class BiodinfoTranslateBO {
    private String canonicalName;
    private String chineseName;
    private String fullName;
    private String id;
    private boolean isParent;
    private String treeId;
}
