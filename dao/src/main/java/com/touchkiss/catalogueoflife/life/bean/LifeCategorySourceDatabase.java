package com.touchkiss.catalogueoflife.life.bean;

import lombok.Data;

/**
 * Created on 2020-03-19 13:04:20
 *
 * @author Touchkiss
 */
@Data
public class LifeCategorySourceDatabase {
    private Integer id; //
    private Integer lifeId; //
    private Integer databaseId; //
    private Long createTime; //
    private Long lastModifyTime; //
}
