package com.touchkiss.catalogueoflife.spider.species2000cn.controller;

import com.touchkiss.catalogueoflife.response.Result;
import com.touchkiss.catalogueoflife.spider.species2000cn.bean.TreeItemResponse;
import com.touchkiss.catalogueoflife.spider.species2000cn.services.Species2000CnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2020/07/07 16:20
 *
 * @author Touchkiss
 */
@Slf4j
@RestController
@RequestMapping("species2000cn")
public class Species2000cnController {
    private final Species2000CnService species2000CnService;

    public Species2000cnController(Species2000CnService species2000CnService) {
        this.species2000CnService = species2000CnService;
    }

    @PostMapping("browse")
    public Result<List<TreeItemResponse>> browser(@RequestParam(name = "id", required = false) String id) {
        List<TreeItemResponse> browse = species2000CnService.browse(id);
        if (CollectionUtils.isEmpty(browse)) {
            return Result.fail("请求的资源不存在");
        } else {
            return Result.success(browse);
        }
    }
}
