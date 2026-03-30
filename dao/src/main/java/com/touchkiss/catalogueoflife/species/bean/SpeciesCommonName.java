package com.touchkiss.catalogueoflife.species.bean;

import lombok.Data;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Data
public class SpeciesCommonName {
    private Integer id; //
    private String speciesId; //物种id
    private String name; //俗名
    private String language; //语言
    private String country; //国家
    private Long createTime; //
    private Long lastModifyTime; //
}
