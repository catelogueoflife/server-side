package com.touchkiss.catalogueoflife.spider.species.bean.convertor;

import com.touchkiss.catalogueoflife.species.bean.SpeciesRecordScrutiny;
import com.touchkiss.catalogueoflife.spider.base.BaseConvertor;
import com.touchkiss.catalogueoflife.spider.species.bean.bo.SpeciesRecordScrutinyBO;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 11:17
 *
 * @author Touchkiss
 */
@Component
public class SpeciesRecordScrutinyConvertor implements BaseConvertor<SpeciesRecordScrutiny, SpeciesRecordScrutinyBO> {
    @Override
    public SpeciesRecordScrutiny convert(SpeciesRecordScrutinyBO speciesRecordScrutinyBO) {
        if (speciesRecordScrutinyBO == null) {
            return null;
        }
        SpeciesRecordScrutiny speciesRecordScrutiny = new SpeciesRecordScrutiny();
        speciesRecordScrutiny.setScrutiny(speciesRecordScrutinyBO.getScrutiny());
        return speciesRecordScrutiny;
    }
}
