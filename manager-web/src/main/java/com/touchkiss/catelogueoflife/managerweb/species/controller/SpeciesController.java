package com.touchkiss.catelogueoflife.managerweb.species.controller;

import com.touchkiss.catelogueoflife.base.PageResult;
import com.touchkiss.catelogueoflife.species.bean.Species;
import com.touchkiss.catelogueoflife.species.services.SpeciesDaoService;
import com.touchkiss.catelogueoflife.controller.BaseController;
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
    public Species getOne(@PathVariable("id") String id) {
        return speciesDaoService.selectById(id);
    }

    @DeleteMapping("/species/{id}")
    public boolean deleteOne(@PathVariable("id") String id) {
        return speciesDaoService.deleteById(id);
    }

    @PostMapping("species")
    public boolean insertOne(@RequestBody Species species) {
        return speciesDaoService.insert(species);
    }

    @PutMapping("species/{id}")
    public boolean updateOne(@RequestBody Species species, @PathVariable("id") String id) {
        return speciesDaoService.updateById(species, id);
    }

    @PatchMapping("species/{id}")
    public boolean updateSelective(@RequestBody Species species, @PathVariable("id") String id) {
        return speciesDaoService.updateSelectiveById(species, id);
    }
}
