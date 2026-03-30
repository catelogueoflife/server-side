package com.touchkiss.catalogueoflife.spider.life.controller;

import com.touchkiss.catalogueoflife.controller.BaseController;
import com.touchkiss.catalogueoflife.response.Result;
import com.touchkiss.catalogueoflife.spider.life.services.LifeCategorySpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生命分类爬虫触发接口（catalogueoflife.org）
 *
 * @author Touchkiss
 */
@Slf4j
@RestController
@RequestMapping("spider/life")
public class LifeCategorySpiderController implements BaseController {

    private final LifeCategorySpiderService lifeCategoryService;

    public LifeCategorySpiderController(LifeCategorySpiderService lifeCategoryService) {
        this.lifeCategoryService = lifeCategoryService;
    }

    @GetMapping("lifeCategory/{id}")
    public Result<Integer> lifeCategorySpider(@PathVariable("id") Integer id) {
        return Result.success(lifeCategoryService.fetchLifeCategoryFromWeb(id));
    }
}
