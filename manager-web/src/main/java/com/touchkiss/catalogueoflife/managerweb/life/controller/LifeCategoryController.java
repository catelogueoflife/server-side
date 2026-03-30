package com.touchkiss.catalogueoflife.managerweb.life.controller;

import com.touchkiss.catalogueoflife.base.PageResult;
import com.touchkiss.catalogueoflife.controller.BaseController;
import com.touchkiss.catalogueoflife.life.bean.LifeCategory;
import com.touchkiss.catalogueoflife.life.services.LifeCategoryDaoService;
import com.touchkiss.catalogueoflife.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created on 2020-03-19 16:45:10
 *
 * @author Touchkiss
 */
@Slf4j
@RestController
@RequestMapping("life")
public class LifeCategoryController implements BaseController {
    private final LifeCategoryDaoService lifeCategoryDaoService;

    public LifeCategoryController(LifeCategoryDaoService lifeCategoryDaoService) {
        this.lifeCategoryDaoService = lifeCategoryDaoService;
    }

    @GetMapping("lifeCategory")
    public PageResult list(HttpServletRequest request, Integer p, Integer pagesize) {
        Map<String, String> whereMap = parameterMap(request);
        int pageNo = p == null ? 1 : p;
        int size = pagesize == null ? 10 : pagesize;
        return new PageResult(lifeCategoryDaoService.findByPage(pageNo, size, whereMap));
    }

    @GetMapping("/lifeCategory/{id}")
    public Result<LifeCategory> getOne(@PathVariable("id") Integer id) {
        return Result.success(lifeCategoryDaoService.selectById(id));
    }

    @DeleteMapping("/lifeCategory/{id}")
    public Result<Boolean> deleteOne(@PathVariable("id") Integer id) {
        return Result.success(lifeCategoryDaoService.deleteById(id));
    }

    @PostMapping("lifeCategory")
    public Result<Boolean> insertOne(@RequestBody LifeCategory lifeCategory) {
        return Result.success(lifeCategoryDaoService.insert(lifeCategory));
    }

    @PutMapping("lifeCategory/{id}")
    public Result<Boolean> updateOne(@RequestBody LifeCategory lifeCategory, @PathVariable("id") Integer id) {
        return Result.success(lifeCategoryDaoService.updateById(lifeCategory, id));
    }

    @PatchMapping("lifeCategory/{id}")
    public Result<Boolean> updateSelective(@RequestBody LifeCategory lifeCategory, @PathVariable("id") Integer id) {
        return Result.success(lifeCategoryDaoService.updateSelectiveById(lifeCategory, id));
    }

    @PostMapping("tree/{id}")
    public Result<List<LifeCategory>> tree(@PathVariable("id") Integer id) {
        return Result.success(lifeCategoryDaoService.queryTree(id));
    }
}
