package com.touchkiss.catalogueoflife.life.bean;

import lombok.Data;

/**
 * Created on 2020-03-19 13:04:20
 *
 * @author Touchkiss
 */
@Data
public class SourceDatabase {
    private Integer id; //
    private String shortName; //
    private String fullName; //
    private Long createTime; //
    private Long lastModifyTime; //
}
