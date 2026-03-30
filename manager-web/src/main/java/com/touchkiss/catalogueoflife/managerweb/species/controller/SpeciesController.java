package com.touchkiss.catalogueoflife.managerweb.species.controller;

import com.touchkiss.catalogueoflife.base.PageResult;
import com.touchkiss.catalogueoflife.controller.BaseController;
import com.touchkiss.catalogueoflife.response.Result;
import com.touchkiss.catalogueoflife.species.bean.Species;
import com.touchkiss.catalogueoflife.species.services.SpeciesDaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created on 2020-03-21 15:07:06
 *
 * @author Touchkiss
 */
@Slf4j
@RestController
@RequestMapping("species")
public class SpeciesController implements BaseController {
    private final SpeciesDaoService speciesDaoService;

    public SpeciesController(SpeciesDaoService speciesDaoService) {
        this.speciesDaoService = speciesDaoService;
    }

    @GetMapping("species")
    public PageResult list(HttpServletRequest request, Integer p, Integer pagesize) {
        Map<String, String> whereMap = parameterMap(request);
        int pageNo = p == null ? 1 : p;
        int size = pagesize == null ? 10 : pagesize;
        return new PageResult(speciesDaoService.findByPage(pageNo, size, whereMap));
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
