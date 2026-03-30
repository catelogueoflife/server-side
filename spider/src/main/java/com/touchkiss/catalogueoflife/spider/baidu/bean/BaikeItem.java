package com.touchkiss.catalogueoflife.spider.baidu.bean;

import com.touchkiss.catalogueoflife.utils.GsonUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2020/04/07 10:33
 *
 * @author Touchkiss
 */
public class BaikeItem {
    private String title;
    private String sourceUrl;
    private Long fetchTime;
    private Map<String, String> properties;
    private Set<String> images;
    private Set<String> links;

    public BaikeItem(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        this.fetchTime = System.currentTimeMillis();
        this.properties = new HashMap<>();
        this.images = new HashSet<>();
        this.links = new HashSet<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public Long getFetchTime() {
        return fetchTime;
    }

    public void setFetchTime(Long fetchTime) {
        this.fetchTime = fetchTime;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public Set<String> getLinks() {
        return links;
    }

    public void setLinks(Set<String> links) {
        this.links = links;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("title", this.title);
        map.put("sourceUrl", this.sourceUrl);
        map.put("fetchTime", this.fetchTime.toString());
        map.putAll(this.properties);
        map.put("images", GsonUtil.toJson(this.images));
        map.put("links", GsonUtil.toJson(this.links));
        return map;
    }

    public void addProperty(String key, String value) {
        this.properties.put(key, value);
    }

    public void addImage(String image) {
        this.images.add(image);
    }

    public void addLink(String link) {
        this.links.add(link);
    }
}
