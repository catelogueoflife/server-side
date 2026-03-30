package com.touchkiss.catalogueoflife.spider.species.bean.convertor;

import com.touchkiss.catalogueoflife.species.bean.SpeciesReferences;
import com.touchkiss.catalogueoflife.spider.base.BaseConvertor;
import com.touchkiss.catalogueoflife.spider.species.bean.bo.SpeciesReferencesBO;
import com.touchkiss.catalogueoflife.utils.NumberUtil;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 11:22
 *
 * @author Touchkiss
 */
@Component
public class SpeciesReferencesConvertor implements BaseConvertor<SpeciesReferences, SpeciesReferencesBO> {
    @Override
    public SpeciesReferences convert(SpeciesReferencesBO speciesReferencesBO) {
        if (speciesReferencesBO == null) {
            return null;
        }
        SpeciesReferences speciesReferences = new SpeciesReferences();
        speciesReferences.setAuthor(speciesReferencesBO.getAuthor());
        speciesReferences.setYear(NumberUtil.stringToInt(speciesReferencesBO.getYear()));
        speciesReferences.setSource(speciesReferencesBO.getSource());
        return speciesReferences;
    }
}
