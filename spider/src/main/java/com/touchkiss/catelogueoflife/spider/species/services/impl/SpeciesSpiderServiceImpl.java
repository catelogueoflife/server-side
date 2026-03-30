package com.touchkiss.catelogueoflife.spider.species.services.impl;

import com.touchkiss.catelogueoflife.life.bean.LifeCategory;
import com.touchkiss.catelogueoflife.life.services.LifeCategoryDaoService;
import com.touchkiss.catelogueoflife.species.bean.*;
import com.touchkiss.catelogueoflife.species.services.*;
import com.touchkiss.catelogueoflife.spider.constants.UrlConstants;
import com.touchkiss.catelogueoflife.spider.species.bean.bo.SpeciesBO;
import com.touchkiss.catelogueoflife.spider.species.bean.convertor.*;
import com.touchkiss.catelogueoflife.spider.species.bean.response.SpeciesResponse;
import com.touchkiss.catelogueoflife.spider.species.services.SpeciesSpiderService;
import com.touchkiss.catelogueoflife.utils.GsonUtil;
import com.touchkiss.catelogueoflife.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Created on 2020/03/20 18:24
 *
 * @author Touchkiss
 */
@Service
public class SpeciesSpiderServiceImpl implements SpeciesSpiderService {
    Pattern ID_PATTERN = Pattern.compile("[a-z0-9]{32}");
    @Autowired
    private LifeCategoryDaoService lifeCategoryDaoService;
    @Autowired
    private SpeciesCommonNameConvertor speciesCommonNameConvertor;
    @Autowired
    private SpeciesCommonNameReferencesConvertor speciesCommonNameReferencesConvertor;
    @Autowired
    private SpeciesConvertor speciesConvertor;
    @Autowired
    private SpeciesRecordScrutinyConvertor speciesRecordScrutinyConvertor;
    @Autowired
    private SpeciesReferencesConvertor speciesReferencesConvertor;
    @Autowired
    private SpeciesSynonymsConvertor speciesSynonymsConvertor;
    @Autowired
    private SpeciesCommonNameDaoService speciesCommonNameDaoService;
    @Autowired
    private SpeciesCommonNameReferencesDaoService speciesCommonNameReferencesDaoService;
    @Autowired
    private SpeciesDaoService speciesDaoService;
    @Autowired
    private SpeciesRecordScrutinyDaoService speciesRecordScrutinyDaoService;
    @Autowired
    private SpeciesReferencesDaoService speciesReferencesDaoService;
    @Autowired
    private SpeciesSynonymsDaoService speciesSynonymsDaoService;
    @Autowired
    @Qualifier("spiderTaskExecutor")
    private Executor spiderTaskExecutor;

    @Override
    public boolean fetchSpecies(String id) throws IOException {
        String response = HttpUtil.get(UrlConstants.SPECIES_FETCH_URL + id);
        SpeciesResponse speciesResponse = GsonUtil.fromJson(response, SpeciesResponse.class);
        if (speciesResponse != null && !CollectionUtils.isEmpty(speciesResponse.getResults())) {
            for (SpeciesBO speciesBO : speciesResponse.getResults()) {
                if (speciesDaoService.selectById(speciesBO.getId()) != null) {
                    continue;
                }
                Species species = speciesConvertor.convert(speciesBO);
                if (species != null) {
                    if (speciesDaoService.insert(species)) {
                        speciesBO.getCommon_names().forEach(speciesCommonNameBO -> {
                            SpeciesCommonName speciesCommonName = speciesCommonNameConvertor.convert(speciesCommonNameBO);
                            if (speciesCommonName != null) {
                                speciesCommonName.setSpeciesId(speciesBO.getId());
                                if (speciesCommonNameDaoService.insert(speciesCommonName)) {
                                    speciesCommonNameBO.getReferences().forEach(speciesCommonNameReferencesBO -> {
                                        SpeciesCommonNameReferences speciesCommonNameReferences = speciesCommonNameReferencesConvertor.convert(speciesCommonNameReferencesBO);
                                        if (speciesCommonNameReferences != null) {
                                            speciesCommonNameReferences.setCommonNameId(speciesCommonName.getId());
                                            speciesCommonNameReferencesDaoService.insert(speciesCommonNameReferences);
                                        }
                                    });
                                }
                            }
                        });
                        SpeciesRecordScrutiny speciesRecordScrutiny = speciesRecordScrutinyConvertor.convert(speciesBO.getRecord_scrutiny_date());
                        if (speciesRecordScrutiny != null) {
                            speciesRecordScrutiny.setSpeciesId(species.getId());
                            speciesRecordScrutinyDaoService.insert(speciesRecordScrutiny);
                        }
                        speciesBO.getReferences().forEach(speciesReferencesBO -> {
                            SpeciesReferences speciesReferences = speciesReferencesConvertor.convert(speciesReferencesBO);
                            if (speciesReferences != null) {
                                speciesReferences.setSpeciesId(species.getId());
                                speciesReferencesDaoService.insert(speciesReferences);
                            }
                        });
                        speciesBO.getSynonyms().forEach(speciesSynonymsBO -> {
                            Species synonyms = speciesSynonymsConvertor.convert(speciesSynonymsBO);
                            if (synonyms != null && speciesDaoService.insert(synonyms)) {
                                SpeciesSynonyms speciesSynonyms = new SpeciesSynonyms();
                                speciesSynonyms.setSpeciesId(species.getId());
                                speciesSynonyms.setSynonymsId(synonyms.getId());
                                speciesSynonymsDaoService.insert(speciesSynonyms);
                                SpeciesRecordScrutiny synonymsScrutiny = new SpeciesRecordScrutiny();
                                synonymsScrutiny.setSpeciesId(synonyms.getId());
                                synonymsScrutiny.setScrutiny(speciesSynonymsBO.getRecord_scrutiny_date());
                                speciesRecordScrutinyDaoService.insert(synonymsScrutiny);
                                speciesSynonymsBO.getReferences().forEach(synonymsReferencesBO -> {
                                    SpeciesReferences synonymsReferences = speciesReferencesConvertor.convert(synonymsReferencesBO);
                                    if (synonymsReferences != null) {
                                        synonymsReferences.setSpeciesId(synonyms.getId());
                                        speciesReferencesDaoService.insert(synonymsReferences);
                                    }
                                });
                            }
                        });
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void autoFetch() {
        List<LifeCategory> list = lifeCategoryDaoService.queryLifeCategoryList(new HashMap() {{
            put("fetched", "0");
            put("type", "species");
            put("limit", "limit 20");
        }});
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(lifeCategory -> 
                CompletableFuture.runAsync(() -> {
                    Matcher matcher = ID_PATTERN.matcher(lifeCategory.getUrl());
                    if (matcher.find()) {
                        String speciesId = matcher.group(0);
                        try {
                            if (fetchSpecies(speciesId)) {
                                lifeCategory.setFetched(true);
                                lifeCategoryDaoService.updateById(lifeCategory, lifeCategory.getId());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, spiderTaskExecutor)
            );
        }
    }
}
