package com.touchkiss.catalogueoflife.spider.wikipedia.services.impl;

import com.touchkiss.catalogueoflife.aop.Timer;
import com.touchkiss.catalogueoflife.spider.constants.RedisConstants;
import com.touchkiss.catalogueoflife.spider.constants.UrlConstants;
import com.touchkiss.catalogueoflife.spider.wikipedia.bean.WikiQueryResponse;
import com.touchkiss.catalogueoflife.spider.wikipedia.services.WikiService;
import com.touchkiss.catalogueoflife.utils.GsonUtil;
import com.touchkiss.catalogueoflife.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2020/04/02 17:12
 *
 * @author Touchkiss
 */
@Slf4j
@Service
public class WikiServiceImpl implements WikiService {

    private final StringRedisTemplate stringRedisTemplate;
    private final Executor spiderTaskExecutor;
    private static final Pattern INNER_LINK_PATTERN = Pattern.compile("(?<=\\[\\[)[^\\]]+(?=\\]\\])");

    public WikiServiceImpl(StringRedisTemplate stringRedisTemplate,
                           @Qualifier("spiderTaskExecutor") Executor spiderTaskExecutor) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.spiderTaskExecutor = spiderTaskExecutor;
    }

    @Override
    @Timer
    public void fetchContent(String title) {
        WikiQueryResponse wikiQueryResponse = null;
        if (stringRedisTemplate.opsForSet().isMember(RedisConstants.WIKI_QUERY_HAS_FETCHED, title)) {
            return;
        }
        try {
            wikiQueryResponse = GsonUtil.fromJson(HttpUtil.get(UrlConstants.WIKI_QUERY_URL + title), WikiQueryResponse.class);
        } catch (IOException e) {
            log.error("获取Wiki内容出错, title: {}", title, e);
        }
        if (wikiQueryResponse != null) {
            try {
                stringRedisTemplate.opsForSet().add(RedisConstants.WIKI_QUERY_HAS_FETCHED, title);
                String content = wikiQueryResponse.getQuery().getPages().get(0).getRevisions().get(0).getContent();
                File targetDir = new File("data/spider/wiki-contents");
                if (!targetDir.exists()) {
                    targetDir.mkdirs();
                }
                File file = new File(targetDir, title + ".txt");
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    return;
                }
                FileUtils.writeStringToFile(file, content, "utf-8");
                Matcher matcher = INNER_LINK_PATTERN.matcher(content);
                while (matcher.find()) {
                    String nextTitle = matcher.group(0);
                    if (nextTitle.contains("|")) {
                        nextTitle = nextTitle.substring(0, nextTitle.indexOf("|"));
                    }
                    nextTitle = nextTitle.replace(" ", "_");
                    if (!stringRedisTemplate.opsForSet().isMember(RedisConstants.WIKI_QUERY_NOT_FETCHED, nextTitle) && !stringRedisTemplate.opsForSet().isMember(RedisConstants.WIKI_QUERY_HAS_FETCHED, nextTitle)) {
                        stringRedisTemplate.opsForSet().add(RedisConstants.WIKI_QUERY_NOT_FETCHED, nextTitle);
                    }
                }
            } catch (Exception e) {
                log.error("保存Wiki内容或解析内链出错, title: {}", title, e);
            }
        }
    }

    @Override
    public void autoFetch() {
        List<String> titles = stringRedisTemplate.opsForSet().pop(RedisConstants.WIKI_QUERY_NOT_FETCHED, 10);
        if (titles != null && !titles.isEmpty()) {
            titles.forEach(title ->
                    CompletableFuture.runAsync(() -> this.fetchContent(title), spiderTaskExecutor)
            );
        }
    }
}
