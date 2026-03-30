package com.touchkiss.catelogueoflife.spider.kuaishou.controller;

import com.touchkiss.catelogueoflife.base.PageResult;
import com.touchkiss.catelogueoflife.controller.BaseController;
import com.touchkiss.catelogueoflife.kuaishou.bean.KsRecVideo;
import com.touchkiss.catelogueoflife.kuaishou.services.KsRecVideoDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created on 2020-08-26 21:20:44
 *
 * @author Touchkiss
 */
@RestController
@RequestMapping("kuaishou")
public class KsRecVideoController implements BaseController {
    @Autowired
    private KsRecVideoDaoService ksRecVideoDaoService;

    @GetMapping("ksRecVideo")
    public PageResult list(Integer draw, HttpServletRequest request, Integer start, Integer length) {
        HashMap<String, String> whereMap = parameterMapJQD(request);
        start = start == null ? 1 : start;
        length = length == null ? 10 : length;
        return new PageResult(draw, ksRecVideoDaoService.findByPage(start, length, whereMap));
    }

    @GetMapping("/ksRecVideo/{photoId}")
    public KsRecVideo getOne(@PathVariable("photoId") String photoId) {
        return ksRecVideoDaoService.selectByPhotoId(photoId);
    }

    @DeleteMapping("/ksRecVideo/{photoId}")
    public boolean deleteOne(@PathVariable("photoId") String photoId) {
        return ksRecVideoDaoService.deleteByPhotoId(photoId);
    }

    @PostMapping("ksRecVideo")
    public boolean insertOne(@RequestBody KsRecVideo ksRecVideo) {
        return ksRecVideoDaoService.insert(ksRecVideo);
    }

    @PutMapping("ksRecVideo/{photoId}")
    public boolean updateOne(@RequestBody KsRecVideo ksRecVideo, @PathVariable("photoId") String photoId) {
        return ksRecVideoDaoService.updateByPhotoId(ksRecVideo, photoId);
    }

    @PatchMapping("ksRecVideo/{photoId}")
    public boolean updateSelective(@RequestBody KsRecVideo ksRecVideo, @PathVariable("photoId") String photoId) {
        return ksRecVideoDaoService.updateSelectiveByPhotoId(ksRecVideo, photoId);
    }
}
