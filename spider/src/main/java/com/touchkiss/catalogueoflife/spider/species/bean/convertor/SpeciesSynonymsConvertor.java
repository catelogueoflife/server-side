package com.touchkiss.catalogueoflife.spider.species.bean.convertor;

import com.touchkiss.catalogueoflife.species.bean.Species;
import com.touchkiss.catalogueoflife.spider.base.BaseConvertor;
import com.touchkiss.catalogueoflife.spider.species.bean.bo.SpeciesSynonymsBO;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 11:09
 *
 * @author Touchkiss
 */
@Component
public class SpeciesSynonymsConvertor implements BaseConvertor<Species, SpeciesSynonymsBO> {
    @Override
    public Species convert(SpeciesSynonymsBO speciesSynonymsBO) {
        if (speciesSynonymsBO == null) {
            return null;
        }
        Species species = new Species();
        species.setId(speciesSynonymsBO.getId());
        species.setName(speciesSynonymsBO.getName());
        species.setNameAccepted("accepted name".equalsIgnoreCase(speciesSynonymsBO.getName_status()));
        species.setGenus(speciesSynonymsBO.getGenus());
        species.setSubgenus(speciesSynonymsBO.getSubgenus());
        species.setSpecies(speciesSynonymsBO.getSpecies());
        species.setAuthor(speciesSynonymsBO.getAuthor());
        species.setInfraspeciesMarker(speciesSynonymsBO.getInfraspecies_marker());
        species.setInfraspecies(speciesSynonymsBO.getInfraspecies());
        species.setOnlineResource(speciesSynonymsBO.getOnline_resource());
        species.setExtinct(speciesSynonymsBO.getIs_extinct() == null ? null : speciesSynonymsBO.getIs_extinct().equals(1));
        species.setSourceDatabase(speciesSynonymsBO.getSource_database());
        species.setSourceDatabaseUrl(speciesSynonymsBO.getSource_database_url());
        species.setBibliographicCitation(speciesSynonymsBO.getBibliographic_citation());
        species.setUrl(speciesSynonymsBO.getUrl());
        species.setDistribution(speciesSynonymsBO.getDistribution());
        return species;
    }
}
