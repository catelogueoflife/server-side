package com.touchkiss.catelogueoflife.managerweb.life.controller;

import com.touchkiss.catelogueoflife.base.PageResult;
import com.touchkiss.catelogueoflife.life.bean.LifeCategory;
import com.touchkiss.catelogueoflife.life.services.LifeCategoryDaoService;
import com.touchkiss.catelogueoflife.controller.BaseController;
import com.touchkiss.catelogueoflife.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created on 2020-03-19 16:45:10
 *
 * @author Touchkiss
 */
@RestController
@RequestMapping("life")
public class LifeCategoryController implements BaseController {
    @Autowired
    private LifeCategoryDaoService lifeCategoryDaoService;

    @GetMapping("lifeCategory")
    public PageResult list(HttpServletRequest request, Integer p, Integer pagesize) {
        HashMap<String, String> whereMap = parameterMap(request);
        return new PageResult(lifeCategoryDaoService.findByPage(p == null ? 1 : p, pagesize == null ? 10 : pagesize, whereMap));
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
    public Result<List<LifeCategory>> tree(@PathVariable("id") Integer id){
        return Result.success(lifeCategoryDaoService.queryTree(id));
    }
}
