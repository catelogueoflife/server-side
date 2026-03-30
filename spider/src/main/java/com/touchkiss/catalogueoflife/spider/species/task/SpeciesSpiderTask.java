package com.touchkiss.catalogueoflife.spider.species.task;

import com.touchkiss.catalogueoflife.spider.species.services.SpeciesSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/25 15:39
 *
 * @author Touchkiss
 */
@Component
@Slf4j
public class SpeciesSpiderTask {
    private final SpeciesSpiderService speciesSpiderService;

    public SpeciesSpiderTask(SpeciesSpiderService speciesSpiderService) {
        this.speciesSpiderService = speciesSpiderService;
    }

    //    @Scheduled(cron = "* * * * * ?")
    public void autoFetch() {
        log.info("====================定时任务抓取物种详情: 开始==========");
        speciesSpiderService.autoFetch();
        log.info("====================定时任务抓取物种详情: 结束==========");
    }
}
