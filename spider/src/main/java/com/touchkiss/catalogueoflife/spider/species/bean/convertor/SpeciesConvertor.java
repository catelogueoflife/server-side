package com.touchkiss.catalogueoflife.spider.species.bean.convertor;

import com.touchkiss.catalogueoflife.species.bean.Species;
import com.touchkiss.catalogueoflife.spider.base.BaseConvertor;
import com.touchkiss.catalogueoflife.spider.species.bean.bo.SpeciesBO;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 11:09
 *
 * @author Touchkiss
 */
@Component
public class SpeciesConvertor implements BaseConvertor<Species, SpeciesBO> {
    @Override
    public Species convert(SpeciesBO speciesBO) {
        if (speciesBO == null) {
            return null;
        }
        Species species = new Species();
        species.setId(speciesBO.getId());
        species.setName(speciesBO.getName());
        species.setNameAccepted("accepted name".equalsIgnoreCase(speciesBO.getName_status()));
        species.setGenus(speciesBO.getGenus());
        species.setSubgenus(speciesBO.getSubgenus());
        species.setSpecies(speciesBO.getSpecies());
        species.setAuthor(speciesBO.getAuthor());
        species.setInfraspeciesMarker(speciesBO.getInfraspecies_marker());
        species.setInfraspecies(speciesBO.getInfraspecies());
        species.setOnlineResource(speciesBO.getOnline_resource());
        species.setExtinct(speciesBO.getIs_extinct() == null ? null : speciesBO.getIs_extinct().equals(1));
        species.setSourceDatabase(speciesBO.getSource_database());
        species.setSourceDatabaseUrl(speciesBO.getSource_database_url());
        species.setBibliographicCitation(speciesBO.getBibliographic_citation());
        species.setUrl(speciesBO.getUrl());
        species.setDistribution(speciesBO.getDistribution());
        return species;
    }
}
