package com.touchkiss.catelogueoflife.managerweb.species.controller;

import com.touchkiss.catelogueoflife.base.PageResult;
import com.touchkiss.catelogueoflife.species.bean.Species;
import com.touchkiss.catelogueoflife.species.services.SpeciesDaoService;
import com.touchkiss.catelogueoflife.controller.BaseController;
import com.touchkiss.catelogueoflife.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created on 2020-03-21 15:07:06
 *
 * @author Touchkiss
 */
@RestController
@RequestMapping("species")
public class SpeciesController implements BaseController {
    @Autowired
    private SpeciesDaoService speciesDaoService;

    @GetMapping("species")
    public PageResult list(HttpServletRequest request, Integer p, Integer pagesize) {
        HashMap<String, String> whereMap = parameterMap(request);
        return new PageResult(speciesDaoService.findByPage(p == null ? 1 : p, pagesize == null ? 10 : pagesize, whereMap));
    }

    @GetMapping("/species/{id}")
    public Result<Species> getOne(@PathVariable("id") String id) {
        return Result.success(speciesDaoService.selectById(id));
    }

    @DeleteMapping("/species/{id}")
    public Result<Boolean> deleteOne(@PathVariable("id") String id) {
        return Result.success(speciesDaoService.deleteById(id));
    }

    @PostMapping("species")
    public Result<Boolean> insertOne(@RequestBody Species species) {
        return Result.success(speciesDaoService.insert(species));
    }

    @PutMapping("species/{id}")
    public Result<Boolean> updateOne(@RequestBody Species species, @PathVariable("id") String id) {
        return Result.success(speciesDaoService.updateById(species, id));
    }

    @PatchMapping("species/{id}")
    public Result<Boolean> updateSelective(@RequestBody Species species, @PathVariable("id") String id) {
        return Result.success(speciesDaoService.updateSelectiveById(species, id));
    }
}
