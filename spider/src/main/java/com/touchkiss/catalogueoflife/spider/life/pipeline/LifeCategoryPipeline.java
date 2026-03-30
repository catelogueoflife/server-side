package com.touchkiss.catalogueoflife.spider.life.pipeline;

import com.touchkiss.catalogueoflife.life.bean.LifeCategory;
import com.touchkiss.catalogueoflife.life.bean.LifeCategorySourceDatabase;
import com.touchkiss.catalogueoflife.life.bean.SourceDatabase;
import com.touchkiss.catalogueoflife.life.services.LifeCategoryDaoService;
import com.touchkiss.catalogueoflife.life.services.LifeCategorySourceDatabaseDaoService;
import com.touchkiss.catalogueoflife.life.services.SourceDatabaseDaoService;
import com.touchkiss.catalogueoflife.spider.life.bean.bo.LifeCategoryBO;
import com.touchkiss.catalogueoflife.spider.life.bean.convertor.LifeCategoryConvertor;
import com.touchkiss.catalogueoflife.spider.life.bean.convertor.SourceDatabaseConvertor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LifeCategoryPipeline implements Pipeline {

    private final LifeCategoryDaoService lifeCategoryDaoService;
    private final SourceDatabaseDaoService sourceDatabaseDaoService;
    private final LifeCategorySourceDatabaseDaoService lifeCategorySourceDatabaseDaoService;
    private final LifeCategoryConvertor lifeCategoryConvertor;
    private final SourceDatabaseConvertor sourceDatabaseConvertor;

    public LifeCategoryPipeline(LifeCategoryDaoService lifeCategoryDaoService,
                                SourceDatabaseDaoService sourceDatabaseDaoService,
                                LifeCategorySourceDatabaseDaoService lifeCategorySourceDatabaseDaoService,
                                LifeCategoryConvertor lifeCategoryConvertor,
                                SourceDatabaseConvertor sourceDatabaseConvertor) {
        this.lifeCategoryDaoService = lifeCategoryDaoService;
        this.sourceDatabaseDaoService = sourceDatabaseDaoService;
        this.lifeCategorySourceDatabaseDaoService = lifeCategorySourceDatabaseDaoService;
        this.lifeCategoryConvertor = lifeCategoryConvertor;
        this.sourceDatabaseConvertor = sourceDatabaseConvertor;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(ResultItems resultItems, Task task) {
        List<LifeCategoryBO> items = resultItems.get("results");
        if (CollectionUtils.isEmpty(items)) {
            return;
        }

        for (LifeCategoryBO lifeCategoryBO : items) {
            try {
                LifeCategory lifeCategory = lifeCategoryConvertor.convert(lifeCategoryBO);
                if (lifeCategory != null) {
                    if (lifeCategoryDaoService.selectById(lifeCategory.getId()) == null) {
                        lifeCategory.setFetched(false);
                        lifeCategoryDaoService.insert(lifeCategory);
                    }

                    if (!CollectionUtils.isEmpty(lifeCategoryBO.getSource_databases())) {
                        lifeCategoryBO.getSource_databases().forEach(sourceDatabaseBO -> {
                            if (sourceDatabaseDaoService.selectById(sourceDatabaseBO.getSource_database_id()) == null) {
                                SourceDatabase sourceDatabase = sourceDatabaseConvertor.convert(sourceDatabaseBO);
                                sourceDatabaseDaoService.insert(sourceDatabase);
                            }

                            Map<String, String> params = new HashMap<>(4);
                            params.put("lifeId", String.valueOf(lifeCategory.getId()));
                            params.put("databaseId", String.valueOf(sourceDatabaseBO.getSource_database_id()));
                            List<?> references = lifeCategorySourceDatabaseDaoService.queryLifeCategorySourceDatabaseList(params);

                            if (CollectionUtils.isEmpty(references)) {
                                LifeCategorySourceDatabase lifeCategorySourceDatabase = new LifeCategorySourceDatabase();
                                lifeCategorySourceDatabase.setLifeId(lifeCategory.getId());
                                lifeCategorySourceDatabase.setDatabaseId(sourceDatabaseBO.getSource_database_id());
                                lifeCategorySourceDatabaseDaoService.insert(lifeCategorySourceDatabase);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                log.error("处理生命分类抓取结果失败, lifeId: {}", lifeCategoryBO.getId(), e);
            }
        }
    }
}
