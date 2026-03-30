package com.touchkiss.catalogueoflife.species.bean;

import lombok.Data;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Data
public class SpeciesReferences {
    private Integer id; //
    private String speciesId; //
    private String author; //
    private Integer year; //
    private String source; //
    private Long createTime; //
    private Long lastModifyTime; //
}
