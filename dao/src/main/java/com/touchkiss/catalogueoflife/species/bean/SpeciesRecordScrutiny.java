package com.touchkiss.catalogueoflife.species.bean;

import lombok.Data;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Data
public class SpeciesRecordScrutiny {
    private Integer id; //
    private String speciesId; //物种id
    private String scrutiny; //审查日期
    private Long createTime; //
    private Long lastModifyTime; //
}
