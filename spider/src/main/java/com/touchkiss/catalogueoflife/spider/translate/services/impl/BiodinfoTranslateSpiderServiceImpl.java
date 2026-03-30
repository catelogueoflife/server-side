package com.touchkiss.catalogueoflife.spider.translate.services.impl;

import com.touchkiss.catalogueoflife.spider.constants.UrlConstants;
import com.touchkiss.catalogueoflife.spider.translate.pipeline.BiodinfoTranslatePipeline;
import com.touchkiss.catalogueoflife.spider.translate.processor.BiodinfoTranslateProcessor;
import com.touchkiss.catalogueoflife.spider.translate.services.BiodinfoTranslateSpiderService;
import com.touchkiss.catalogueoflife.translate.bean.Translate;
import com.touchkiss.catalogueoflife.translate.services.TranslateDaoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * 生物翻译爬虫服务实现（biodinfo.org）
 *
 * <p>遵循 Processor/Pipeline 分离原则：</p>
 * <ul>
 *   <li>{@link BiodinfoTranslateProcessor} 负责网络响应解析</li>
 *   <li>{@link BiodinfoTranslatePipeline} 负责数据持久化</li>
 * </ul>
 *
 * @author Touchkiss
 */
@Slf4j
@Service
public class BiodinfoTranslateSpiderServiceImpl implements BiodinfoTranslateSpiderService {

    private final BiodinfoTranslateProcessor biodinfoTranslateProcessor;
    private final BiodinfoTranslatePipeline biodinfoTranslatePipeline;
    private final TranslateDaoService translateDaoService;
    private final Executor spiderTaskExecutor;

    public BiodinfoTranslateSpiderServiceImpl(BiodinfoTranslateProcessor biodinfoTranslateProcessor,
                                              BiodinfoTranslatePipeline biodinfoTranslatePipeline,
                                              TranslateDaoService translateDaoService,
                                              @Qualifier("spiderTaskExecutor") Executor spiderTaskExecutor) {
        this.biodinfoTranslateProcessor = biodinfoTranslateProcessor;
        this.biodinfoTranslatePipeline = biodinfoTranslatePipeline;
        this.translateDaoService = translateDaoService;
        this.spiderTaskExecutor = spiderTaskExecutor;
    }

    @Override
    public boolean fetchTranslate(String id, String treeid) {
        Request request = buildRequest(id, treeid);
        try {
            Spider.create(biodinfoTranslateProcessor)
                    .addRequest(request)
                    .addPipeline(biodinfoTranslatePipeline)
                    .thread(1)
                    .run();
        } catch (Exception e) {
            log.error("抓取生物翻译数据失败, treeid: {}, id: {}", treeid, id, e);
            return false;
        }
        return true;
    }

    @Override
    public void autoFetch() {
        Map<String, String> params = new HashMap<>(4);
        params.put("fetched", "0");
        params.put("limit", "limit 10");
        List<Translate> list = translateDaoService.queryTranslateList(params);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(translate ->
                    CompletableFuture.runAsync(() -> {
                        try {
                            fetchTranslate(translate.getLid(), translate.getTreeId());
                        } catch (Exception e) {
                            log.error("自动抓取翻译信息失败, lid: {}, treeId: {}",
                                    translate.getLid(), translate.getTreeId(), e);
                        }
                    }, spiderTaskExecutor)
            );
        }
    }

    /**
     * 构造 POST 请求，携带 lid/treeid 参数供 Pipeline 使用。
     */
    private static Request buildRequest(String id, String treeid) {
        String url = UrlConstants.BIODINFO_FETCH_URL + "?treeid=" + treeid;
        if (StringUtils.isNotBlank(id)) {
            url += "&id=" + id;
        }
        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);
        if (StringUtils.isNotBlank(id)) {
            request.putExtra("lid", id);
        }
        request.putExtra("treeid", treeid);
        return request;
    }
}
