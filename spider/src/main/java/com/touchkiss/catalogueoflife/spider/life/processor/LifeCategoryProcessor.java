package com.touchkiss.catalogueoflife.spider.life.processor;

import com.touchkiss.catalogueoflife.spider.life.bean.response.LifeCategoryResponse;
import com.touchkiss.catalogueoflife.utils.GsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class LifeCategoryProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        String json = page.getJson().get();
        LifeCategoryResponse lifeCategoryResponse = GsonUtil.fromJson(json, LifeCategoryResponse.class);
        if (lifeCategoryResponse == null || CollectionUtils.isEmpty(lifeCategoryResponse.getItems())) {
            page.setSkip(true);
        } else {
            page.putField("results", lifeCategoryResponse.getItems());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
