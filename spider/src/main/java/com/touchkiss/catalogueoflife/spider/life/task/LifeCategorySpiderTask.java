package com.touchkiss.catalogueoflife.spider.life.task;

import com.touchkiss.catalogueoflife.spider.life.services.LifeCategorySpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 17:59
 *
 * @author Touchkiss
 */
@Component
@Slf4j
public class LifeCategorySpiderTask {
    private final LifeCategorySpiderService lifeCategorySpiderService;

    public LifeCategorySpiderTask(LifeCategorySpiderService lifeCategorySpiderService) {
        this.lifeCategorySpiderService = lifeCategorySpiderService;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void autoFetch() {
        log.info("====================定时任务抓取物种分类: 开始==========");
        lifeCategorySpiderService.autoFetch();
        log.info("====================定时任务抓取物种分类: 结束==========");
    }
}
