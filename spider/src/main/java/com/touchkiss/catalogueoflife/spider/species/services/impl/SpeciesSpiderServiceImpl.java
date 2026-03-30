package com.touchkiss.catalogueoflife.spider.species.services.impl;

import com.touchkiss.catalogueoflife.life.bean.LifeCategory;
import com.touchkiss.catalogueoflife.life.services.LifeCategoryDaoService;
import com.touchkiss.catalogueoflife.spider.constants.UrlConstants;
import com.touchkiss.catalogueoflife.spider.species.pipeline.SpeciesPipeline;
import com.touchkiss.catalogueoflife.spider.species.processor.SpeciesProcessor;
import com.touchkiss.catalogueoflife.spider.species.services.SpeciesSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2020/03/20 18:24
 *
 * @author Touchkiss
 */
@Slf4j
@Service
public class SpeciesSpiderServiceImpl implements SpeciesSpiderService {
    private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9]{32}");

    private final LifeCategoryDaoService lifeCategoryDaoService;
    private final SpeciesProcessor speciesProcessor;
    private final SpeciesPipeline speciesPipeline;
    private final Executor spiderTaskExecutor;

    public SpeciesSpiderServiceImpl(LifeCategoryDaoService lifeCategoryDaoService,
                                    SpeciesProcessor speciesProcessor,
                                    SpeciesPipeline speciesPipeline,
                                    @Qualifier("spiderTaskExecutor") Executor spiderTaskExecutor) {
        this.lifeCategoryDaoService = lifeCategoryDaoService;
        this.speciesProcessor = speciesProcessor;
        this.speciesPipeline = speciesPipeline;
        this.spiderTaskExecutor = spiderTaskExecutor;
    }

    @Override
    public boolean fetchSpecies(String id) {
        Spider.create(speciesProcessor)
                .addUrl(UrlConstants.SPECIES_FETCH_URL + id)
                .addPipeline(speciesPipeline)
                .thread(1)
                .run();
        return true;
    }

    @Override
    public void autoFetch() {
        Map<String, Object> params = new HashMap<>(4);
        params.put("fetched_eq", 0);
        params.put("type_eq", "species");
        params.put("limit", "limit 20");
        List<LifeCategory> list = lifeCategoryDaoService.queryLifeCategoryList(params);
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
                            } catch (Exception e) {
                                log.error("自动抓取物种信息失败, speciesId: {}", speciesId, e);
                            }
                        }
                    }, spiderTaskExecutor)
            );
        }
    }
}
