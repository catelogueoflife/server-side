package com.touchkiss.catelogueoflife.spider.translate.services.impl;

import com.touchkiss.catelogueoflife.spider.constants.UrlConstants;
import com.touchkiss.catelogueoflife.spider.translate.bean.convertor.BiodinfoTranslateConvertor;
import com.touchkiss.catelogueoflife.spider.translate.bean.response.BiodinfoTranslateResponse;
import com.touchkiss.catelogueoflife.spider.translate.services.BiodinfoTranslateSpiderService;
import com.touchkiss.catelogueoflife.translate.bean.Translate;
import com.touchkiss.catelogueoflife.translate.services.TranslateDaoService;
import com.touchkiss.catelogueoflife.utils.GsonUtil;
import com.touchkiss.catelogueoflife.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 2020/03/21 17:12
 *
 * @author Touchkiss
 */
@Service
public class BiodinfoTranslateSpiderServiceImpl implements BiodinfoTranslateSpiderService {
    @Autowired
    private BiodinfoTranslateConvertor biodinfoTranslateConvertor;
    @Autowired
    private TranslateDaoService translateDaoService;
    @Autowired
    @Qualifier("spiderTaskExecutor")
    private Executor spiderTaskExecutor;

    @Override
    public boolean fetchTranslate(String id, String treeid) throws IOException {
        String url = UrlConstants.Biodinfo_FETCH_URL + "?treeid=" + treeid;
        boolean notBlank = StringUtils.isNotBlank(id);
        if (notBlank) {
            url += "&id=" + id;
        }
        String response = HttpUtil.post(url, "");
        BiodinfoTranslateResponse biodinfoTranslateResponse = GsonUtil.fromJson(response, BiodinfoTranslateResponse.class);
        if (biodinfoTranslateResponse != null) {
            biodinfoTranslateResponse.forEach(biodinfoTranslateBO -> {
                if (CollectionUtils.isEmpty(translateDaoService.queryTranslateList(new HashMap() {{
                    put("canonicalName", biodinfoTranslateBO.getCanonicalName());
                    put("language", "Chinese");
                }})) || StringUtils.isBlank(id)) {
                    Translate translate = biodinfoTranslateConvertor.convert(biodinfoTranslateBO);
                    translateDaoService.insert(translate);
                }
            });
        }
        if (notBlank) {
            List<Translate> lid = translateDaoService.queryTranslateList(new HashMap() {{
                put("lid", id);
            }});
            if (!CollectionUtils.isEmpty(lid)) {
                lid.stream().peek(translate -> translate.setFetched(true)).forEach(translate -> translateDaoService.updateById(translate, translate.getId()));
            }
        }
        return true;
    }

    @Override
    public void autoFetch() {
        List<Translate> list = translateDaoService.queryTranslateList(new HashMap() {{
            put("fetched", "0");
            put("limit", "limit 10");
        }});
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(translate -> {
                CompletableFuture.runAsync(() -> {
                    try {
                        fetchTranslate(translate.getLid(), translate.getTreeId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, spiderTaskExecutor);
            });
        }
    }
}

