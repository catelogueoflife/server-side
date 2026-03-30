package com.touchkiss.catalogueoflife.spider.species.bean.bo;

import lombok.Data;

import java.util.List;

/**
 * Created on 2020/03/20 18:08
 *
 * @author Touchkiss
 */
@Data
public class SpeciesCommonNameBO {
    private String name;
    private String language;
    private String country;
    private List<SpeciesCommonNameReferencesBO> references;
}
