package com.touchkiss.catalogueoflife.spider.species.pipeline;

import com.touchkiss.catalogueoflife.species.bean.*;
import com.touchkiss.catalogueoflife.species.services.*;
import com.touchkiss.catalogueoflife.spider.species.bean.bo.SpeciesBO;
import com.touchkiss.catalogueoflife.spider.species.bean.convertor.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Slf4j
@Component
public class SpeciesPipeline implements Pipeline {
    private final SpeciesCommonNameConvertor speciesCommonNameConvertor;
    private final SpeciesCommonNameReferencesConvertor speciesCommonNameReferencesConvertor;
    private final SpeciesConvertor speciesConvertor;
    private final SpeciesRecordScrutinyConvertor speciesRecordScrutinyConvertor;
    private final SpeciesReferencesConvertor speciesReferencesConvertor;
    private final SpeciesSynonymsConvertor speciesSynonymsConvertor;
    private final SpeciesCommonNameDaoService speciesCommonNameDaoService;
    private final SpeciesCommonNameReferencesDaoService speciesCommonNameReferencesDaoService;
    private final SpeciesDaoService speciesDaoService;
    private final SpeciesRecordScrutinyDaoService speciesRecordScrutinyDaoService;
    private final SpeciesReferencesDaoService speciesReferencesDaoService;
    private final SpeciesSynonymsDaoService speciesSynonymsDaoService;

    public SpeciesPipeline(SpeciesCommonNameConvertor speciesCommonNameConvertor,
                           SpeciesCommonNameReferencesConvertor speciesCommonNameReferencesConvertor,
                           SpeciesConvertor speciesConvertor,
                           SpeciesRecordScrutinyConvertor speciesRecordScrutinyConvertor,
                           SpeciesReferencesConvertor speciesReferencesConvertor,
                           SpeciesSynonymsConvertor speciesSynonymsConvertor,
                           SpeciesCommonNameDaoService speciesCommonNameDaoService,
                           SpeciesCommonNameReferencesDaoService speciesCommonNameReferencesDaoService,
                           SpeciesDaoService speciesDaoService,
                           SpeciesRecordScrutinyDaoService speciesRecordScrutinyDaoService,
                           SpeciesReferencesDaoService speciesReferencesDaoService,
                           SpeciesSynonymsDaoService speciesSynonymsDaoService) {
        this.speciesCommonNameConvertor = speciesCommonNameConvertor;
        this.speciesCommonNameReferencesConvertor = speciesCommonNameReferencesConvertor;
        this.speciesConvertor = speciesConvertor;
        this.speciesRecordScrutinyConvertor = speciesRecordScrutinyConvertor;
        this.speciesReferencesConvertor = speciesReferencesConvertor;
        this.speciesSynonymsConvertor = speciesSynonymsConvertor;
        this.speciesCommonNameDaoService = speciesCommonNameDaoService;
        this.speciesCommonNameReferencesDaoService = speciesCommonNameReferencesDaoService;
        this.speciesDaoService = speciesDaoService;
        this.speciesRecordScrutinyDaoService = speciesRecordScrutinyDaoService;
        this.speciesReferencesDaoService = speciesReferencesDaoService;
        this.speciesSynonymsDaoService = speciesSynonymsDaoService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(ResultItems resultItems, Task task) {
        List<SpeciesBO> results = resultItems.get("results");
        if (results == null) {
            return;
        }

        for (SpeciesBO speciesBO : results) {
            try {
                if (speciesDaoService.selectById(speciesBO.getId()) != null) {
                    continue;
                }
                Species species = speciesConvertor.convert(speciesBO);
                if (species != null) {
                    if (speciesDaoService.insert(species)) {
                        saveCommonNames(speciesBO, species.getId());
                        saveRecordScrutiny(speciesBO, species.getId());
                        saveReferences(speciesBO, species.getId());
                        saveSynonyms(speciesBO, species.getId());
                    }
                }
            } catch (Exception e) {
                log.error("处理物种抓取结果存库失败, speciesId: {}", speciesBO.getId(), e);
            }
        }
    }

    private void saveCommonNames(SpeciesBO speciesBO, String speciesId) {
        if (speciesBO.getCommon_names() == null) {
            return;
        }
        speciesBO.getCommon_names().forEach(speciesCommonNameBO -> {
            try {
                SpeciesCommonName speciesCommonName = speciesCommonNameConvertor.convert(speciesCommonNameBO);
                if (speciesCommonName != null) {
                    speciesCommonName.setSpeciesId(speciesId);
                    if (speciesCommonNameDaoService.insert(speciesCommonName)) {
                        if (speciesCommonNameBO.getReferences() != null) {
                            speciesCommonNameBO.getReferences().forEach(refBO -> {
                                SpeciesCommonNameReferences ref = speciesCommonNameReferencesConvertor.convert(refBO);
                                if (ref != null) {
                                    ref.setCommonNameId(speciesCommonName.getId());
                                    speciesCommonNameReferencesDaoService.insert(ref);
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                log.error("保存物种常用名失败, speciesId: {}", speciesId, e);
            }
        });
    }

    private void saveRecordScrutiny(SpeciesBO speciesBO, String speciesId) {
        if (speciesBO.getRecord_scrutiny_date() == null) {
            return;
        }
        try {
            SpeciesRecordScrutiny speciesRecordScrutiny = speciesRecordScrutinyConvertor.convert(speciesBO.getRecord_scrutiny_date());
            if (speciesRecordScrutiny != null) {
                speciesRecordScrutiny.setSpeciesId(speciesId);
                speciesRecordScrutinyDaoService.insert(speciesRecordScrutiny);
            }
        } catch (Exception e) {
            log.error("保存物种审查信息失败, speciesId: {}", speciesId, e);
        }
    }

    private void saveReferences(SpeciesBO speciesBO, String speciesId) {
        if (speciesBO.getReferences() == null) {
            return;
        }
        try {
            speciesBO.getReferences().forEach(refBO -> {
                SpeciesReferences ref = speciesReferencesConvertor.convert(refBO);
                if (ref != null) {
                    ref.setSpeciesId(speciesId);
                    speciesReferencesDaoService.insert(ref);
                }
            });
        } catch (Exception e) {
            log.error("保存物种参考信息失败, speciesId: {}", speciesId, e);
        }
    }

    private void saveSynonyms(SpeciesBO speciesBO, String speciesId) {
        if (speciesBO.getSynonyms() == null) {
            return;
        }
        speciesBO.getSynonyms().forEach(speciesSynonymsBO -> {
            try {
                Species synonyms = speciesSynonymsConvertor.convert(speciesSynonymsBO);
                if (synonyms != null && speciesDaoService.insert(synonyms)) {
                    SpeciesSynonyms speciesSynonyms = new SpeciesSynonyms();
                    speciesSynonyms.setSpeciesId(speciesId);
                    speciesSynonyms.setSynonymsId(synonyms.getId());
                    speciesSynonymsDaoService.insert(speciesSynonyms);

                    SpeciesRecordScrutiny synonymsScrutiny = new SpeciesRecordScrutiny();
                    synonymsScrutiny.setSpeciesId(synonyms.getId());
                    synonymsScrutiny.setScrutiny(speciesSynonymsBO.getRecord_scrutiny_date());
                    speciesRecordScrutinyDaoService.insert(synonymsScrutiny);

                    if (speciesSynonymsBO.getReferences() != null) {
                        speciesSynonymsBO.getReferences().forEach(refBO -> {
                            SpeciesReferences ref = speciesReferencesConvertor.convert(refBO);
                            if (ref != null) {
                                ref.setSpeciesId(synonyms.getId());
                                speciesReferencesDaoService.insert(ref);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                log.error("保存物种同物异名失败, speciesId: {}", speciesId, e);
            }
        });
    }
}
