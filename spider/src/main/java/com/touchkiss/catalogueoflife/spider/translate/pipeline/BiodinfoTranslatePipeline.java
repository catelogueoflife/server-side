package com.touchkiss.catalogueoflife.spider.translate.pipeline;

import com.touchkiss.catalogueoflife.spider.translate.bean.bo.BiodinfoTranslateBO;
import com.touchkiss.catalogueoflife.spider.translate.bean.convertor.BiodinfoTranslateConvertor;
import com.touchkiss.catalogueoflife.translate.bean.Translate;
import com.touchkiss.catalogueoflife.translate.services.TranslateDaoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生物翻译数据持久化管道（biodinfo.org）
 * <p>
 * 从 {@link ResultItems} 中取出解析好的 BO 列表，执行去重后写入 {@code t_translate} 表。
 * 若请求携带 {@code lid}，写入完成后同步将对应记录标记为 fetched。
 * </p>
 */
@Slf4j
@Component
public class BiodinfoTranslatePipeline implements Pipeline {

    private final BiodinfoTranslateConvertor biodinfoTranslateConvertor;
    private final TranslateDaoService translateDaoService;

    public BiodinfoTranslatePipeline(BiodinfoTranslateConvertor biodinfoTranslateConvertor,
                                     TranslateDaoService translateDaoService) {
        this.biodinfoTranslateConvertor = biodinfoTranslateConvertor;
        this.translateDaoService = translateDaoService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<BiodinfoTranslateBO> items = resultItems.get("translates");
        String lid = resultItems.get("lid");

        if (CollectionUtils.isEmpty(items)) {
            return;
        }

        for (BiodinfoTranslateBO bo : items) {
            try {
                saveIfAbsent(bo, lid);
            } catch (Exception e) {
                log.error("保存生物翻译数据失败, canonicalName: {}", bo.getCanonicalName(), e);
            }
        }

        // 叶节点请求：更新 fetched 状态
        if (StringUtils.isNotBlank(lid)) {
            markFetched(lid);
        }
    }

    /**
     * 按 canonicalName + language 去重，不存在时插入；
     * 当 lid 为空（全量拉取模式）时，始终执行更新插入。
     */
    private void saveIfAbsent(BiodinfoTranslateBO bo, String lid) {
        Map<String, String> params = new HashMap<>(4);
        params.put("canonicalName", bo.getCanonicalName());
        params.put("language", "Chinese");

        boolean exists = !CollectionUtils.isEmpty(translateDaoService.queryTranslateList(params));
        if (!exists || StringUtils.isBlank(lid)) {
            Translate translate = biodinfoTranslateConvertor.convert(bo);
            if (translate != null) {
                translateDaoService.insert(translate);
            }
        }
    }

    private void markFetched(String lid) {
        Map<String, String> params = new HashMap<>(2);
        params.put("lid", lid);
        List<Translate> list = translateDaoService.queryTranslateList(params);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(translate -> {
                translate.setFetched(true);
                translateDaoService.updateById(translate, translate.getId());
            });
        }
    }
}
