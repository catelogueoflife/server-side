package com.touchkiss.catalogueoflife.spider.species.bean.bo;

import lombok.Data;

import java.util.List;

/**
 * Created on 2020/03/20 18:17
 *
 * @author Touchkiss
 */
@Data
public class SpeciesSynonymsBO {
    private String id;
    private String name;
    private String rank;
    private String name_status;
    private String genus;
    private String subgenus;
    private String species;
    private String infraspecies_marker;
    private String infraspecies;
    private String author;
    private String record_scrutiny_date;
    private String online_resource;
    private String is_extinct;
    private String source_database;
    private String source_database_url;
    private String bibliographic_citation;
    private String name_html;
    private String url;
    private String distribution;
    private List<SpeciesReferencesBO> references;
}
