package com.touchkiss.catalogueoflife.spider.translate.task;

import com.touchkiss.catalogueoflife.spider.translate.services.BiodinfoTranslateSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created on 2020/03/21 17:30
 *
 * @author Touchkiss
 */
@Component
@Slf4j
public class BiodinfoSpiderTask {
    private final BiodinfoTranslateSpiderService biodinfoTranslateSpiderService;

    public BiodinfoSpiderTask(BiodinfoTranslateSpiderService biodinfoTranslateSpiderService) {
        this.biodinfoTranslateSpiderService = biodinfoTranslateSpiderService;
    }

    //    @Scheduled(cron = "* * * * * ?")
    public void autoFetch() {
        log.info("====================定时任务抓取中文翻译: 开始==========");
        biodinfoTranslateSpiderService.autoFetch();
        log.info("====================定时任务抓取中文翻译: 结束==========");
    }
}
