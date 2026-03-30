package com.touchkiss.catalogueoflife.spider.translate.controller;

import com.touchkiss.catalogueoflife.response.Result;
import com.touchkiss.catalogueoflife.spider.translate.services.BiodinfoTranslateSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生物翻译爬虫触发接口（biodinfo.org）
 *
 * <p>初始 treeid 参考值：
 * <ul>
 *   <li>11b1c7f7-80df-42b0-b0d5-8e770a245c26</li>
 *   <li>f359fe39-0bee-4f69-89a2-f86758d256e6</li>
 *   <li>cc9550f4-3c0c-40fa-a497-c90a6dacbeb1</li>
 *   <li>fc90163c-c149-424d-b3bb-ab9b4689096c</li>
 *   <li>e17f16f8-f0c5-41f0-a30c-dab5ff962854</li>
 * </ul>
 * </p>
 *
 * @author Touchkiss
 */
@Slf4j
@RestController
@RequestMapping("spider/biodinfo")
public class BiodinfoTranslateSpiderController {

    private final BiodinfoTranslateSpiderService biodinfoTranslateSpiderService;

    public BiodinfoTranslateSpiderController(BiodinfoTranslateSpiderService biodinfoTranslateSpiderService) {
        this.biodinfoTranslateSpiderService = biodinfoTranslateSpiderService;
    }

    /**
     * 抓取指定叶节点的翻译数据
     */
    @GetMapping("{treeid}/{id}")
    public Result<Boolean> biodinfoTranslateSpider(@PathVariable("treeid") String treeid,
                                                   @PathVariable("id") String id) {
        return Result.success(biodinfoTranslateSpiderService.fetchTranslate(id, treeid));
    }

    /**
     * 全量抓取指定 treeid 下的翻译数据
     */
    @GetMapping("{treeid}")
    public Result<Boolean> biodinfoTranslateSpiderByTree(@PathVariable("treeid") String treeid) {
        return Result.success(biodinfoTranslateSpiderService.fetchTranslate(null, treeid));
    }
}
