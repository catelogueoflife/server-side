package com.touchkiss.catalogueoflife.spider.baidu.controller;

import com.touchkiss.catalogueoflife.es.repositories.impl.BaseRepositoryImpl;
import com.touchkiss.catalogueoflife.response.Result;
import com.touchkiss.catalogueoflife.spider.baidu.bean.BaikeItemResponse;
import com.touchkiss.catalogueoflife.spider.baidu.bean.ESBaikeItem;
import com.touchkiss.catalogueoflife.spider.baidu.services.BaikeService;
import com.touchkiss.catalogueoflife.spider.constants.RedisConstants;
import com.touchkiss.catalogueoflife.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.indices.CreateIndexResponse;
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
@Slf4j
@RestController
@RequestMapping("baike")
public class BaiduBaikeController {
    private final BaseRepositoryImpl baseRepository;
    private final StringRedisTemplate stringRedisTemplate;
    private final BaikeService baikeService;

    public BaiduBaikeController(BaseRepositoryImpl baseRepository,
                                StringRedisTemplate stringRedisTemplate,
                                BaikeService baikeService) {
        this.baseRepository = baseRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.baikeService = baikeService;
    }

    @GetMapping("createIndex")
    public Result<CreateIndexResponse> createIndex() {
        return Result.success(baseRepository.createIndex(ESBaikeItem.class));
    }

    @GetMapping("compare")
    public Result<Void> compare() {
        Set<String> keys = stringRedisTemplate.keys("*");
        if (keys != null) {
            Set<String> members = stringRedisTemplate.opsForSet().members(RedisConstants.BAIKE_HAS_STORED_TO_ES);
            if (members != null) {
                keys.removeAll(members);
            }
            Long add = stringRedisTemplate.opsForSet().add(RedisConstants.BAIKE_NOT_STORED_TO_ES, StringUtil.toStringArray(keys));
            log.info("将 {} 个key放入redis", add);
        }
        return Result.success(null);
    }

    @PostMapping("query")
    public Result<List<BaikeItemResponse>> query(@RequestParam("word") String word) {
        List<BaikeItemResponse> query = baikeService.query(word);
        if (CollectionUtils.isEmpty(query)) {
            return Result.fail("请求的资源不存在");
        } else {
            return Result.success(query);
        }
    }
}




