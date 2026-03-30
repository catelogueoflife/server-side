package com.touchkiss.catalogueoflife.spider.species2000cn.services.impl;

import com.touchkiss.catalogueoflife.spider.species2000cn.bean.TreeItemResponse;
import com.touchkiss.catalogueoflife.spider.species2000cn.processor.Species2000CnProcessor;
import com.touchkiss.catalogueoflife.spider.species2000cn.services.Species2000CnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created on 2020/07/07 16:27
 *
 * @author Touchkiss
 */
@Slf4j
@Service
public class Species2000CnServiceImpl implements Species2000CnService {
    private final Species2000CnProcessor species2000CnProcessor;

    public Species2000CnServiceImpl(Species2000CnProcessor species2000CnProcessor) {
        this.species2000CnProcessor = species2000CnProcessor;
    }

    @Override
    public List<TreeItemResponse> browse(String id) {
        AtomicReference<List<TreeItemResponse>> resultRef = new AtomicReference<>();

        Pipeline syncPipeline = (resultItems, task) -> {
            List<TreeItemResponse> results = resultItems.get("results");
            if (results != null) {
                resultRef.set(results);
            }
        };

        try {
            Spider.create(species2000CnProcessor)
                    .addRequest(Species2000CnProcessor.buildDataRequest(id))
                    .addPipeline(syncPipeline)
                    .thread(1)
                    .run();
        } catch (Exception e) {
            log.error("抓取物种2000(CN)树信息失败, id: {}", id, e);
        }

        List<TreeItemResponse> finalRes = resultRef.get();
        return finalRes != null ? finalRes : Collections.emptyList();
    }
}
