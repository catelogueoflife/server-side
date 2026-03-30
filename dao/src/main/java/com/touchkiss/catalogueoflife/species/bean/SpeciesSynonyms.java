package com.touchkiss.catalogueoflife.species.bean;

import lombok.Data;

/**
 * Created on 2020-03-20 17:31:57
 *
 * @author Touchkiss
 */
@Data
public class SpeciesSynonyms {
    private Integer id; //
    private String speciesId; //接受名物种id
    private String synonymsId; //异名物种id
    private Long createTime; //
    private Long lastModifyTime; //
}
