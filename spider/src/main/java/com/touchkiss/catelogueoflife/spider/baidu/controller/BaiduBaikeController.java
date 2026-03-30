package com.touchkiss.catelogueoflife.spider.baidu.controller;

import com.touchkiss.catelogueoflife.es.repositories.impl.BaseRepositoryImpl;
import com.touchkiss.catelogueoflife.response.BaseResponse;
import com.touchkiss.catelogueoflife.spider.baidu.bean.BaikeItemResponse;
import com.touchkiss.catelogueoflife.spider.baidu.bean.ESBaikeItem;
import com.touchkiss.catelogueoflife.spider.baidu.services.BaikeService;
import com.touchkiss.catelogueoflife.spider.constants.RedisConstants;
import com.touchkiss.catelogueoflife.utils.StringUtil;
import org.apache.http.HttpStatus;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created on 2020/04/15 10:57
 *
 * @author Touchkiss
 */
@RestController
@RequestMapping("baike")
public class BaiduBaikeController {
    @Autowired
    private BaseRepositoryImpl baseRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BaikeService baikeService;

    @RequestMapping("createIndex")
    public CreateIndexResponse createIndex() {
        return baseRepository.createIndex(ESBaikeItem.class);
    }

    @RequestMapping("compare")
    public void compare() {
        Set<String> keys = stringRedisTemplate.keys("*");
        Set<String> members = stringRedisTemplate.opsForSet().members(RedisConstants.BAIKE_HAS_STORED_TO_ES);
        keys.removeAll(members);
        Long add = stringRedisTemplate.opsForSet().add(RedisConstants.BAIKE_NOT_STORED_TO_ES, StringUtil.toStringArray(keys));
        System.out.println("将" + add + "个key放入redsi");
    }

    @PostMapping("query")
    public BaseResponse<List<BaikeItemResponse>> query(@RequestParam("word") String word) {
        List<BaikeItemResponse> query = baikeService.query(word);
        if (CollectionUtils.isEmpty(query)){
            return new BaseResponse<>(HttpStatus.SC_NOT_FOUND,"请求的资源不存在",null);
        }else{
            return new BaseResponse<>(HttpStatus.SC_OK,"success",query);
        }
    }
}




