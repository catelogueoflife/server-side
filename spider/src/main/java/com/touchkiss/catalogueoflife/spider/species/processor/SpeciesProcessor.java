package com.touchkiss.catalogueoflife.spider.species.processor;

import com.touchkiss.catalogueoflife.spider.species.bean.response.SpeciesResponse;
import com.touchkiss.catalogueoflife.utils.GsonUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class SpeciesProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        String json = page.getJson().get();
        SpeciesResponse speciesResponse = GsonUtil.fromJson(json, SpeciesResponse.class);
        if (speciesResponse == null || CollectionUtils.isEmpty(speciesResponse.getResults())) {
            page.setSkip(true);
        } else {
            page.putField("results", speciesResponse.getResults());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
