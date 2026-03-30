package com.touchkiss.catalogueoflife.spider.wikipedia.task;

import com.touchkiss.catalogueoflife.spider.wikipedia.services.WikiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/04/02 17:56
 *
 * @author Touchkiss
 */
@Component
@Slf4j
public class WikiSpiderTask {
    private final WikiService wikiService;

    public WikiSpiderTask(WikiService wikiService) {
        this.wikiService = wikiService;
    }

    //    @Scheduled(cron = "*/10 * * * * ?")
    public void autoFetch() {
        log.info("====================定时任务抓维基百科: 开始==========");
        wikiService.autoFetch();
        log.info("====================定时任务抓维基百科: 结束==========");
    }
}
