package com.touchkiss.catalogueoflife.spider.life.services.impl;

import com.touchkiss.catalogueoflife.life.bean.LifeCategory;
import com.touchkiss.catalogueoflife.life.services.LifeCategoryDaoService;
import com.touchkiss.catalogueoflife.spider.constants.UrlConstants;
import com.touchkiss.catalogueoflife.spider.life.pipeline.LifeCategoryPipeline;
import com.touchkiss.catalogueoflife.spider.life.processor.LifeCategoryProcessor;
import com.touchkiss.catalogueoflife.spider.life.services.LifeCategorySpiderService;
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
 * Created on 2020/03/18 17:33
 *
 * @author Touchkiss
 */
@Service
@Slf4j
public class LifeCategorySpiderServiceImpl implements LifeCategorySpiderService {
    private static final Pattern ID_PATTERN = Pattern.compile("[a-z0-9]{32}");

    private final LifeCategoryDaoService lifeCategoryDaoService;
    private final LifeCategoryProcessor lifeCategoryProcessor;
    private final LifeCategoryPipeline lifeCategoryPipeline;
    private final SpeciesSpiderService speciesSpiderService;
    private final Executor spiderTaskExecutor;

    public LifeCategorySpiderServiceImpl(LifeCategoryDaoService lifeCategoryDaoService,
                                         LifeCategoryProcessor lifeCategoryProcessor,
                                         LifeCategoryPipeline lifeCategoryPipeline,
                                         SpeciesSpiderService speciesSpiderService,
                                         @Qualifier("spiderTaskExecutor") Executor spiderTaskExecutor) {
        this.lifeCategoryDaoService = lifeCategoryDaoService;
        this.lifeCategoryProcessor = lifeCategoryProcessor;
        this.lifeCategoryPipeline = lifeCategoryPipeline;
        this.speciesSpiderService = speciesSpiderService;
        this.spiderTaskExecutor = spiderTaskExecutor;
    }

    @Override
    public int fetchLifeCategoryFromWeb(int id) {
        int result = 0;
        log.info("开始抓取生命分类, id: {}", id);
        LifeCategory lifeCategoryInDb = lifeCategoryDaoService.selectById(id);

        if (lifeCategoryInDb == null || !"species".equals(lifeCategoryInDb.getType())) {
            String url = UrlConstants.LIFE_CATEGORY_TREE_FETCH_URL + id;
            Spider.create(lifeCategoryProcessor)
                    .addUrl(url)
                    .addPipeline(lifeCategoryPipeline)
                    .thread(1)
                    .run();
            result = 1;
        } else {
            String url = lifeCategoryInDb.getUrl();
            Matcher matcher = ID_PATTERN.matcher(url);
            if (matcher.find()) {
                String speciesId = matcher.group(0);
                try {
                    if (speciesSpiderService.fetchSpecies(speciesId)) {
                        result = 1;
                    }
                } catch (Exception e) {
                    log.error("抓取物种详情失败, speciesId: {}", speciesId, e);
                }
            }
        }

        if (lifeCategoryInDb != null) {
            lifeCategoryInDb.setFetched(true);
            lifeCategoryDaoService.updateById(lifeCategoryInDb, id);
        }
        return result;
    }

    @Override
    public void autoFetch() {
        Map<String, Object> params = new HashMap<>(8);
        params.put("id_get", 5000000);
        params.put("fetched_eq", 0);
        params.put("type_ne", "species");
        params.put("limit", "limit 5");

        List<LifeCategory> list = lifeCategoryDaoService.queryLifeCategoryList(params);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(lifeCategory ->
                    CompletableFuture.runAsync(() -> {
                        try {
                            fetchLifeCategoryFromWeb(lifeCategory.getId());
                        } catch (Exception e) {
                            log.error("自动抓取生命分类失败, id: {}", lifeCategory.getId(), e);
                        }
                    }, spiderTaskExecutor)
            );
        }
    }
}
