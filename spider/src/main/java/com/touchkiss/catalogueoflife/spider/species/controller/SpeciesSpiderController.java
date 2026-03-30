package com.touchkiss.catalogueoflife.spider.species.controller;

import com.touchkiss.catalogueoflife.response.Result;
import com.touchkiss.catalogueoflife.spider.species.services.SpeciesSpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物种爬虫触发接口（catalogueoflife.org）
 *
 * @author Touchkiss
 */
@Slf4j
@RestController
@RequestMapping("spider/species")
public class SpeciesSpiderController {

    private final SpeciesSpiderService speciesSpiderService;

    public SpeciesSpiderController(SpeciesSpiderService speciesSpiderService) {
        this.speciesSpiderService = speciesSpiderService;
    }

    @GetMapping("species/{id}")
    public Result<Boolean> speciesSpider(@PathVariable("id") String id) {
        return Result.success(speciesSpiderService.fetchSpecies(id));
    }
}
