package com.touchkiss.catalogueoflife.spider.translate.processor;

import com.google.gson.reflect.TypeToken;
import com.touchkiss.catalogueoflife.spider.translate.bean.bo.BiodinfoTranslateBO;
import com.touchkiss.catalogueoflife.utils.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 生物翻译数据爬取处理器（biodinfo.org）
 * <p>
 * 负责解析 biodinfo.org 接口返回的 JSON，将 BO 列表放入 ResultItems。
 * 通过 request extra 接收参数：
 * <ul>
 *   <li>{@code lid}    - 叶节点 id，空则表示全量拉取</li>
 *   <li>{@code treeid} - 分类树 id</li>
 * </ul>
 * </p>
 */
@Slf4j
@Component
public class BiodinfoTranslateProcessor implements PageProcessor {

    private final Site site = Site.me().setRetryTimes(3).setSleepTime(500);

    @Override
    public void process(Page page) {
        String json = page.getRawText();
        List<BiodinfoTranslateBO> items = null;
        try {
            items = GsonUtil.fromJson(json, new TypeToken<List<BiodinfoTranslateBO>>() {
            }.getType());
        } catch (Exception e) {
            log.warn("解析生物翻译响应失败, url: {}", page.getUrl(), e);
        }

        if (CollectionUtils.isEmpty(items)) {
            log.warn("生物翻译响应为空或解析失败, url: {}", page.getUrl());
            page.setSkip(true);
            return;
        }

        page.putField("translates", items);
        // 将请求参数透传给 Pipeline
        page.putField("lid", page.getRequest().getExtra("lid"));
        page.putField("treeid", page.getRequest().getExtra("treeid"));
    }

    @Override
    public Site getSite() {
        return site;
    }
}
